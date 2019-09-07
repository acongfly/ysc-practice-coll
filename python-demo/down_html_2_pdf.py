import pdfkit

if __name__ == '__main__':

    f2 = open("word_url.txt", "r")
    lines = f2.readlines()
    for line3 in lines:
        print(line3)
        # ++'.pdf'
        replace = line3.replace('\n', '').split('/', 3)[-1].replace('/', '_') + '.pdf'
        # print(replace)
        pdfkit.from_url(line3.replace('\n', ''), replace)
    f2.close()
