import mysql.connector
from mysql.connector import Error

# MySQL Configuration
DB_CONFIG = {
    'host': 'localhost',
    'user': 'root',
    'password': 'admin',  # 🔁 Replace with your actual password
    'database': 'ai_gcp_gcfunctions_cloudrun'
}

# GCP Configuration
GCP_PROJECT_ID = ''  # 🔁 Replace with your project ID
GCP_REGION = 'us-central1'

# MySQL connection
def get_mysql_connection():
    try:
        conn = mysql.connector.connect(**DB_CONFIG)
        return conn
    except Error as e:
        print("MySQL connection error:", e)
        return None

# Store Cloud Functions in DB
def store_functions_to_db(functions):
    conn = get_mysql_connection()
    if not conn: return
    cursor = conn.cursor()
    for f in functions:
        cursor.execute("""
            INSERT INTO gcp_functions (name, runtime, entry_point, url, is_anomaly)
            VALUES (%s, %s, %s, %s, %s)
        """, (
            f.get('name'),
            f.get('runtime'),
            f.get('entry_point'),
            f.get('https_trigger', ''),
            f.get('anomaly', False)
        ))
    conn.commit()
    cursor.close()
    conn.close()

# Store Cloud Run services in DB
def store_services_to_db(services):
    conn = get_mysql_connection()
    if not conn: return
    cursor = conn.cursor()
    for s in services:
        cursor.execute("""
            INSERT INTO gcp_cloudrun_services (name, uri, template, is_anomaly)
            VALUES (%s, %s, %s, %s)
        """, (
            s.get('name'),
            s.get('uri'),
            str(s.get('template')),
            s.get('anomaly', False)
        ))
    conn.commit()
    cursor.close()
    conn.close()
