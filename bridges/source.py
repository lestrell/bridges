import requests
import json

class GeoJSON(object):
    def __init__(self):
        self.url = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/1.0_day.geojson"
    
    def update(self):
        response = json.loads(requests.get(self.url).text)
        
        for item in response['features']:
            yield item # dict(), could be str() or anything else