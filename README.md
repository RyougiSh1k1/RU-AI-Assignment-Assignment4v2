# RU-AI-Assignment1
## repeated forward A* ：

重复以下动作直到1）达到goal或者2）路径上遇到障碍（blocked）。

 对于1） 结束循环

对于2）重新调用A*算法，参数：当前位置、地图已知的障碍物信息

## A*算法 ：

data structure：

1. Priority queue， 根据f(n)的值排序

2. Node: g(n),h(n)f(n),parent,当前位置；
3. 当前地图信息

```
Algorithm :
1) 从queue中取出f(n)值最小的node
2) 找child
3）对每个n': g（n'）=g(n)+1; parent(n')=n;
4) 算出每个n'的f(n'), 更新queue
```



### 注意

1. 如果随机的地图无法达到goal，则表示queue是空的，即遍历所有能遍历的点但是找不到goal
2. 问题：怎么计算h(n)
3. **实际上在沿着预定好的path走的时候，每经过一个unblocked的点的时候，也可以更新地图**
4. 假设h(n)不随着地图的变化而变化
5. 两个地图，一个是完整的，一个是我们目前知道的
6. 我们在开始的时候就知道地图有多大
7. 注意A*效率，不然会扣分。looking up if any element exist or not in your implementation of your closed must be a **constant-time operation**.
8. 如果从某个节点开始是unsolvable，那么要返回去找到第一个碰到的有两个child的节点

