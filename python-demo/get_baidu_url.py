from urllib.request import urlopen

from bs4 import BeautifulSoup


def get_url(url, file_path):
    html = urlopen(url)  # 获取网页
    bs = BeautifulSoup(html, 'html.parser')  # 解析网页
    hyperlink = bs.find_all('a')  # 获取所有超链接

    file = open(file_path, 'a')  # a 是追加，w是覆盖写，r是读取
    # span = bs.find_all('span')
    # # print(span)
    # title = bs.find_all('h1')
    # print(title)

    for tag in bs.find_all('div', class_='vilidate'):
        span = tag.find('span').get_text()
        print(span)

    for tag in bs.find_all('div', class_='single-main-con'):
        title = tag.find('p').get_text()
        print(title)

    for h in hyperlink:
        hh = h.get('href')

        if hh and 'https://pan.baidu.com/' in hh:  # 筛选博客链接
            print(hh)
            file.write(title + '|' + hh + '|' + span)  # 写入到“blog.txt”文件中
            file.write('\n')

    file.close()


if __name__ == '__main__':
    # for i in range(1, 800):
    f2 = open("python_word_url.txt", "r")
    lines = f2.readlines()
    for line3 in lines:
        print(line3)
        get_url(line3, './python_baidu_url.txt')
