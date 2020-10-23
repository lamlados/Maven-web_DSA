import pandas as pd
import matplotlib.pylab as plt
import seaborn as sns
from matplotlib.pylab import style
from statsmodels.tsa.arima.model import ARIMA
from statsmodels.graphics.tsaplots import plot_acf, plot_pacf


style.use('ggplot')
plt.rcParams['font.sans-serif'] = ['SimHei']
plt.rcParams['axes.unicode_minus']=False
stockFile='AAPL.csv'
stock=pd.read_csv(stockFile,index_col=0,parse_dates=[0])
stock.head(10)

#原始数据是股票每天的行情，这里将数据进行重采样，按每周的平均行情来进行分析，‘W-MON’表示按周为单位，指定周一为基准日，即周一到下个周一为一个计算周期
stock_week=stock['Close'].resample('W-MON').mean()
stock_train=stock_week['1980':'2020']
stock_train.plot(figsize=(12,8))
plt.legend(bbox_to_anchor=(1.25,0.5))
plt.title("Stock Close")
sns.despine()
plt.show()

#对数据进行差分，目的使数据平缓,满足平稳性的要求
stock_diff=stock_train.diff()
stock_diff=stock_diff.dropna()
#一阶差分的结果如图所示，可以看出数据基本已经趋于平缓，为了确定一阶差分足以满足需求，再进行ACF和PACF检验，ACF和PACF可以直接调用statemodels里的函数进行求算
#可以看出，进行一阶差分时，结果已经落在了置信区间内（图中蓝色区域），因此可以确定进行一阶差分是可靠有效的
plt.figure()
plt.plot(stock_diff)
plt.title('一阶差分')
plt.show()

acf=plot_acf(stock_diff,lags=20)
plt.title("ACF")
acf.show()
#ARIMA模型训练，参数order=(1,1,1）表示一阶差分，ACF和PACF的取值均为1
model=ARIMA(stock_train,order=(1,1,1),freq='W-MON')
#利用建立好的result模型来预测走势
result=model.fit()
pred=result.predict('20200831','20201023',dynamic=True,typ='levels')
print(pred)

plt.figure(figsize=(6,6))
plt.xticks(rotation=45)
plt.plot(pred)
plt.plot(stock_train)
plt.show()
