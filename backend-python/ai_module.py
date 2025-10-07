import numpy as np
from sklearn.ensemble import IsolationForest

# Prepare feature vectors from function/service metadata
def preprocess_for_ai(data_list):
    def encode_runtime(runtime):
        mapping = {'python39': 1, 'nodejs16': 2, 'go116': 3}
        return mapping.get(runtime, 0)

    X = []
    for item in data_list:
        name_len = len(item.get('name', ''))
        url_len = len(item.get('https_trigger', '') or item.get('uri', ''))
        runtime_encoded = encode_runtime(item.get('runtime', ''))
        X.append([name_len, url_len, runtime_encoded])
    return np.array(X)

# Run IsolationForest to detect anomalies
def detect_anomalies(data_list):
    if not data_list:
        return []

    X = preprocess_for_ai(data_list)
    model = IsolationForest(n_estimators=100, contamination=0.2, random_state=42)
    model.fit(X)
    predictions = model.predict(X)

    for i, item in enumerate(data_list):
        item['anomaly'] = (predictions[i] == -1)
    return data_list
