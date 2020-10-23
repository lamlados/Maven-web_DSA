import matplotlib.pyplot as plt
fr=open('test.txt','r',encoding='UTF-8')
content=fr.readlines()
contentLines=''
characters=[]
rate={}
pic={}

for line in content:
    line=line.strip()
    if(len(line))==0:
        continue
    contentLines+=line
    for x in range(0,len(line)):
        if line[x] not in characters:
            characters.append(line[x])
        if line[x] not in rate:
            rate[line[x]]=0
        rate[line[x]]+=1

rate=sorted(rate.items(),key=lambda x:x[1],reverse=False)
print('共有%d个字'%len(contentLines))
print('共有%d个不同的字'%len(characters))
for i in rate:
    print(i[0],"出现了",i[1],"次")
    pic[i[0]]=i[1]



def draw_from_dict(dicdata,RANGE, head=0):
    #dicdata：字典的数据。
    #RANGE：截取显示的字典的长度。
    by_value = sorted(dicdata.items(),key = lambda item:item[1],reverse=True)
    x = []
    y = []
    for d in by_value:
        x.append(d[0])
        y.append(d[1])
    if head == 0:
        plt.bar(x[0:RANGE], y[0:RANGE])
        plt.show()
        return
    elif head == 1:
        plt.barh(x[0:RANGE], y[0:RANGE])
        plt.show()
        return
    else:
        return "head的值必须为0或1"
draw_from_dict(pic,len(characters),0)
fr.close()
