import requests
import json
import rauth
import webbrowser

class GeoJSON(object):
    def __init__(self):
        self.url = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/1.0_day.geojson"
    
    def update(self):
        response = json.loads(requests.get(self.url).text)
        
        for item in response['features']:
            yield item # dict(), could be str() or anything else

class Twitter(object):
    # Most of this code comes from rauth documentation
    def __init__(self):
        service = rauth.OAuth1Service(
            name='twitter',
            consumer_key='nI49ut8dTNCatlZz2K9eCw',
            consumer_secret='VMDfQgZ4uP5oplFa3cvwu60oKSDb34g5i4PZdS7uabg',
            request_token_url='https://api.twitter.com/oauth/request_token',
            access_token_url='https://api.twitter.com/oauth/access_token',
            authorize_url='https://api.twitter.com/oauth/authorize',
            base_url='https://api.twitter.com/1.1/')
        request_token, request_token_secret = service.get_request_token()
        authorize_url = service.get_authorize_url(request_token)
        # This part is interactive
        print 'Visit this URL in your browser: ' + authorize_url
        webbrowser.open_new_tab(authorize_url)
        pin = raw_input('Enter PIN from browser: ')
        self.session = service.get_auth_session(request_token,
                                   request_token_secret,
                                   method='POST',
                                   data={'oauth_verifier': pin})
    
    def update(self):
        params = {'count': 10}       # 10 tweets

        rjs = self.session.get('statuses/home_timeline.json', params=params)
        rjs = json.loads(rjs.text)
        # stub: reload and yield new ones until you hit a limit, get no more new ones, etc
        for tweet in rjs:
            yield tweet