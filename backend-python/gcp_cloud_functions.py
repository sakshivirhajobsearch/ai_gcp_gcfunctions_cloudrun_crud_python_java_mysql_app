from google.cloud import functions_v1
from config import GCP_PROJECT_ID, GCP_REGION

# Initialize GCP Functions client
client = functions_v1.CloudFunctionsServiceClient()

def list_functions():
    """
    Lists all Google Cloud Functions in the given project and region.
    Returns a list of dictionaries with details.
    """
    parent = f"projects/{GCP_PROJECT_ID}/locations/{GCP_REGION}"
    functions = client.list_functions(request={"parent": parent})

    result = []
    for f in functions:
        result.append({
            'name': f.name,
            'runtime': f.runtime,
            'entry_point': f.entry_point,
            'https_trigger': f.https_trigger.url if f.https_trigger else None
        })
    return result

# Debugging / Direct run
if __name__ == "__main__":
    try:
        from pprint import pprint
        print(f"🔍 Listing Cloud Functions in {GCP_PROJECT_ID} / {GCP_REGION}...\n")
        functions = list_functions()
        if not functions:
            print("⚠️ No Cloud Functions found.")
        else:
            pprint(functions)
    except Exception as e:
        print(f"❌ Error while listing functions: {e}")
