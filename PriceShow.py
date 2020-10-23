import datetime
import pandas_datareader.data as web
from datetime import timedelta

start = datetime.datetime(2020, 6, 1)
end = datetime.datetime(2020, 10, 16)
company=input("请输入要查询的公司：")
df = web.DataReader(company, 'yahoo', start, end)
df.tail()
close_px = df['Adj Close'] #复权后收盘价

import matplotlib.pyplot as plt
from matplotlib import style
import matplotlib as mpl
import yahoo_fin.stock_info as yaf

mpl.rc('figure', figsize=(8, 8))

style.use('ggplot')
close_px.plot(label=company)
plt.legend()



def prev_price(ticker):
    end_data = datetime.datetime.now().date()
    start_date = datetime.datetime.now().date() - timedelta(days=30)
    res_df = yaf.get_data(
        ticker, start_date=start_date, end_date=end_data, interval="1d"
    )
    prev_close = res_df.close.iloc[0]
    return prev_close

def live_price(ticker) :
    price = yaf.get_live_price(ticker)
    return price

price=int(live_price(company))
status=int(price-prev_price(company))
title="Current: "+str(price)+" " +str(status)

if status>0:
    plt.title(title, color='r')
elif status<0:
    plt.title(title, color='g')
plt.show()

