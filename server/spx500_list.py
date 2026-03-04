from io import StringIO

import pandas as pd
import requests
import ssl
from pymongo import MongoClient

# Eğer hala SSL hatası alıyorsan bu kalsın, almıyorsan silebilirsin
ssl._create_default_https_context = ssl._create_unverified_context

def import_spx500():
    url = "https://en.wikipedia.org/wiki/List_of_S%26P_500_companies"

    # Kendimizi tarayıcı olarak tanıtıyoruz
    headers = {
        "User-Agent": "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36"
    }

    try:
        # Sayfayı requests ile çekiyoruz
        response = requests.get(url, headers=headers)

        # Eğer sayfa başarıyla çekildiyse (200 OK)
        if response.status_code == 200:
            # Pandas'a sayfa içeriğini (text) gönderiyoruz
            response_text = StringIO(response.text)
            df = pd.read_html(response_text)[0]

            # Sütun isimlerini düzenleme kısmın buradan devam etsin...
            print("Veri başarıyla çekildi, MongoDB'ye aktarılıyor...")

            # --- Buradan aşağısı senin mevcut kolon düzenleme kodların ---
            column_names = {
                'Symbol': 'symbol',
                'Security': 'security',
                'GICS Sector': 'sector',
                'GICS Sub-Industry': 'sub_industry',
                'Headquarters Location': 'headquarters',
                'Date added': 'date_added',
                'CIK': 'cik',
                'Founded': 'founded_date'
            }
            df = df.rename(columns=column_names)
            df = df[list(column_names.values())]
            df = df.fillna("")

            client = MongoClient("mongodb://localhost:27017")
            db = client["SPX500"]
            collection = db["Companies"]

            collection.delete_many({})

            #df.to_dict(orient="records") şunu ifade eder: Her satırı (row) bir sözlük (dict) yapar, tüm satırları da bir liste içinde döndürür.
            #Yani MongoDB’ye insert_many() için en uygun formatlardan biridir.

            # [
            #     {"symbol": "AAPL", "security": "Apple Inc.", ...},
            #     {"symbol": "MSFT", "security": "Microsoft", ...},
            # ]

            companies_dict = df.to_dict(orient='records')
            collection.insert_many(companies_dict)

        else:
            print(f"Hata oluştu! Durum kodu: {response.status_code}")

    except Exception as e:
        print(f"Bağlantı sırasında bir hata oluştu: {e}")


if __name__ == "__main__":
    import_spx500()