import shutil

import requests
import urllib3

if __name__ == '__main__':
    # url = "https://res.cocolian.cn/rcbfcc/第二代农信银支付清算系统需求规格说明书（清算账户管理系统分册）.pdf"  # Note: It's https
    # r = requests.get(url, verify=False, stream=True)
    # r.raw.decode_content = True
    # with open("第二代农信银支付清算系统需求规格说明书（清算账户管理系统分册）.pdf", 'wb') as f:
    #     shutil.copyfileobj(r.raw, f)

    # read txt method one
    f = open("url.txt")
    line = f.readline()
    while line:
        print(line)
        line = f.readline()
        urllib3.disable_warnings()
        url = line
        fileName = url.split('/')[-1]
        with urllib3.PoolManager() as http:
            r = http.request('GET', url)
            with open(fileName, 'wb') as fout:
                fout.write(r.data)
    f.close()
