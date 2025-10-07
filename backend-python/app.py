from flask import Flask, jsonify, send_from_directory
from gcp_cloud_functions import list_functions
from gcp_cloud_run import list_services

app = Flask(__name__)

# Root route
@app.route("/", methods=["GET"])
def index():
    return "✅ AI GCP CRUD Backend is running. Use /gcp/functions or /gcp/cloudrun to fetch data."

# Favicon route (optional)
@app.route("/favicon.ico")
def favicon():
    return send_from_directory('static', 'favicon.ico')

# Cloud Functions route
@app.route("/gcp/functions", methods=["GET"])
def get_functions():
    try:
        functions = list_functions()
        return jsonify(functions)
    except Exception as e:
        return jsonify({"error": str(e)}), 500

# Cloud Run route
@app.route("/gcp/cloudrun", methods=["GET"])
def get_cloudrun_services():
    try:
        services = list_services()
        return jsonify(services)
    except Exception as e:
        return jsonify({"error": str(e)}), 500

if __name__ == "__main__":
    print("🔹 Flask app is starting...")
    print("Available routes: /, /gcp/functions, /gcp/cloudrun")
    app.run(debug=True)
