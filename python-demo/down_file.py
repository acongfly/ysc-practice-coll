import os

import requests

headers = {
    'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:54.0) Gecko/20100101 Firefox/54.0'
}


def download_file_series(video_links):
    # for link in video_links:
    file_name = video_links.split('/')[-1]

    print("Downloading file:%s" % file_name)
    r = requests.get(video_links, stream=True)

    # download started
    with open(file_name, 'wb') as f:
        for chunk in r.iter_content(chunk_size=1024 * 1024):
            if chunk:
                f.write(chunk)

    print("%s downloaded!\n" % file_name)

    # print("All videos downloaded!")

    return


## 下载pdf
def save_file(url, pdf_name, file_dir):
    response = requests.get(url, headers=headers, stream=True)
    ## 如果指定的文件夹，那么便新建
    if not os.path.exists(file_dir):
        os.makedirs(file_dir)
    ## os.path.join(a,b..)如果a字符串没有以/结尾，那么自动加上\\。（windows下）
    with open(os.path.join(file_dir, pdf_name), "wb") as pdf_file:
        for content in response.iter_content():
            pdf_file.write(content)


if __name__ == '__main__':
    f2 = open("url.txt", "r")
    lines = f2.readlines()
    for line3 in lines:
        print(line3)
        # download_file_series(line3.replace('\n', ''))
        save_file(line3.replace('\n', ''), line3.replace('\n', '').split('/')[-1],
                  "/Users/shicongyang/books/paystudybook")
    f2.close()
