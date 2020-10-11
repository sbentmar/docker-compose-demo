from flask import Flask, request, jsonify
from flask_redis import FlaskRedis
import json

app = Flask(__name__)
app.config['REDIS_URL'] = "redis://@redis:6379/0"
redis_client = FlaskRedis(app)


@app.route('/')
def index():
    return "OK"


@app.route('/todo', methods=['GET'])
def get_all():
    values = {'items': []}
    keys = []
    for key in redis_client.scan_iter():
        keys.append(key.decode('utf-8'))
    keys.sort()
    for key in keys:
        values['items'].append(
            {'key': key, 'value': redis_client.get(key).decode('utf-8')})
    return jsonify(values)


@app.route('/todo/<key>', methods=['DELETE'])
def delete(key):
    old_value = redis_client.get(key).decode('utf-8')
    redis_client.delete(key)
    return jsonify({'key': key, 'value': old_value})


def generate_key():
    keys = []
    for key in redis_client.scan_iter():
        keys.append(int(key.decode('utf-8')))
    keys.sort()
    return int(keys[-1])+1


@app.route('/todo/<key>', methods=['POST'])
def set_value(key):
    value = request.data
    redis_client.set(key, value)
    return jsonify({'key': key, 'value': redis_client.get(key).decode('utf-8')})


@app.route('/todo', methods=['POST'])
def set_value_generate_key():
    return set_value(generate_key())


if __name__ == '__main__':
    app.run(debug=True, host='0.0.0.0')
