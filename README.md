KMeans聚类
====
naive Bayes朴素贝叶斯分类<br>
====
朴素贝叶斯模型，分为两类：多项式模型和伯努利模型<br>
* 多项式模型<br>
贝叶斯分类是一个概率学习的方法，它是通过计算文档d在类别中概率，然后选择最大的分类概率，作为其最佳分类。<br>
计算公式为：![贝叶斯公式](https://nlp.stanford.edu/IR-book/html/htmledition/img865.png)<br>
条件概率：![条件概率](https://nlp.stanford.edu/IR-book/html/htmledition/img866.png)<br><br>
先验概率:	![先验概率](https://nlp.stanford.edu/IR-book/html/htmledition/img870.png)<br><br>

朴素贝叶斯是在贝叶斯思想之上的一个特殊的情况下，即假定各个条件相互独立。<br>
因此各项条件概率进行相乘，为了提高计算的精度，一般会把等式两边取对数。把相乘转换为相加操作。<br>

伪代码逻辑如下：<br>
![伪代码](https://nlp.stanford.edu/IR-book/html/htmledition/img897.png)<br>

若测试集的项在训练集中未出现，计算后验概率时，会出现分母为零。因此对其公式转化为：<br>
![后验概率公式](https://nlp.stanford.edu/IR-book/html/htmledition/img898.png)<br>

* 伯努利模型<br>
努伯利模型再计算后验概率和条件概率时，和多项式不同。算法如下：<br>
![努伯利模型公式](https://nlp.stanford.edu/IR-book/html/htmledition/img926.png)<br>


FPG-Growth
====



