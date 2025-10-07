from google.cloud import run_v2
from config import GCP_PROJECT_ID, GCP_REGION

# Initialize Cloud Run client
client = run_v2.ServicesClient()

def list_services():
    """
    Lists all Cloud Run services in the given project and region.
    Returns a list of dictionaries with service details.
    """
    parent = f"projects/{GCP_PROJECT_ID}/locations/{GCP_REGION}"
    services = client.list_services(request={"parent": parent})

    result = []
    for s in services:
        result.append({
            'name': s.name,
            'uri': s.uri,
            'template': s.template.spec.containers[0].image if s.template.spec.containers else None
        })
    return result

# Debugging / Direct run
if __name__ == "__main__":
    try:
        from pprint import pprint
        print(f"🔍 Listing Cloud Run services in {GCP_PROJECT_ID} / {GCP_REGION}...\n")
        services = list_services()
        if not services:
            print("⚠️ No Cloud Run services found.")
        else:
            pprint(services)
    except Exception as e:
        print(f"❌ Error while listing Cloud Run services: {e}")
