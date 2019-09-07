from urllib.request import urlopen
from bs4 import BeautifulSoup


def get_url(url, file_path):
    html = urlopen(url)  # 获取网页
    bs = BeautifulSoup(html, 'html.parser')  # 解析网页
    hyperlink = bs.find_all('a')  # 获取所有超链接

    file = open(file_path, 'w')

    for h in hyperlink:
        hh = h.get('href')
        if hh and '/essay/' in hh:  # 筛选博客链接
            print(hh)
            file.write('http://doc.cocolian.cn' + hh)  # 写入到“blog.txt”文件中
            file.write('\n')

    file.close()


if __name__ == '__main__':
    get_url('http://doc.cocolian.cn/essay/', './word_url.txt')
