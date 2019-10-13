from urllib.request import urlopen

from bs4 import BeautifulSoup


def get_url(url, file_path):
    html = urlopen(url)  # 获取网页
    bs = BeautifulSoup(html, 'html.parser')  # 解析网页
    hyperlink = bs.find_all('a')  # 获取所有超链接

    file = open(file_path, 'a')

    for h in hyperlink:
        hh = h.get('href')
        if hh and '/down/' in hh and '.html' in hh:  # 筛选博客链接
            print(hh)
            file.write(hh)  # 写入到“blog.txt”文件中
            file.write('\n')

    file.close()


if __name__ == '__main__':
    for i in range(1, 9):
        get_url('https://www.jqhtml.com/down/category/resources/python/page/' + str(i), './python_word_url.txt')
