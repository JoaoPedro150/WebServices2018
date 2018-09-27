# coding: utf-8

import json
import urllib.request as request
import urllib.error
import os

from pathlib import Path

def main():
    try:
        num_seguidores = 0;
        num_seguidores_processados = 0
        seguidores = None

        API = 'https://api.github.com/users/{0}'

        user = getStartUser()

        seguidores = json_request(user, API.format(user + '/followers'), None)

        num_seguidores = json_request(user + '_followers', API.format(user), None)['followers']

        print("\n%s seguidores:" % num_seguidores)

        for seguidor in seguidores:

            print('\n')
            dado_seguidor = json_request(str(seguidor['login']), 'url', seguidor)

            print('%s (%s)' % (dado_seguidor['name'], dado_seguidor['login']))

            repos = json_request(str(seguidor['login']) + '_repos', 'repos_url', dado_seguidor)

            for dado_repos in repos:
                print('       '
                + dado_repos['name'])

            num_seguidores_processados += 1

    except KeyboardInterrupt:
        pass

    except urllib.error.HTTPError as e:
        from datetime import datetime

        print('\n')
        print(json.loads(e.fp.read().decode())['message'])
        print('Limit reset: ' + str(datetime.fromtimestamp(int(e.fp.getheader('X-RateLimit-Reset')))))

    finally:
        print('\n')

        if (num_seguidores > 0):
             print("\nQuantidade de seguidores processados %d/%d" % (num_seguidores_processados,num_seguidores))

def json_request(file_format, json_attribute, json_object):
    path = os.getcwd() + '/cache/'

    if not os.path.exists(path):
        os.makedirs(path)

    path += file_format + '.json'

    cache_file = Path(path)

    if cache_file.is_file():
        with open(path, "r") as arq:
            dados = json.loads(arq.read())
    else:
        if (json_object):
            dados = json.loads(request.urlopen(json_object[json_attribute]).read().decode())
        else:
            dados = json.loads(request.urlopen(json_attribute).read().decode())

        with open(path, "w") as arq:
            json.dump(dados, arq)

    return dados

def getStartUser():
    return input('\nUsuário de consulta: ')

if __name__ == '__main__':
    import sys
    sys.exit(main())
