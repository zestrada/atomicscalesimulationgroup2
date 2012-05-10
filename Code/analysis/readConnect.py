#/usr/python
# Testing
testFile = open("connection100000.dat")
testLines = (testFile.read()).replace('[','').replace('],',']').rstrip(',')
testLine = testLines.split(']')
tn = len(testLine)
tm = 0
test = [[ 0 for i in range(n-2)] for j in range(n-2)]
tx = 0
for ti in testLine:
    ts = ti.rstrip(',').lstrip(',').split(',')
    if(len(ts) > 10):
        test[tx] = ts
        tm = tm + 1
    tx = tx + 1
tn = tm
# Answer
ansFile = open("answer.dat")
ansLines = (ansFile.read()).replace('[','').replace('],',']').rstrip(',')
ansLine = ansLines.split(']')
an = len(testLine)
am = 0
ans = [[ 0 for i in range(n-2)] for j in range(n-2)]
ax = 0
for ai in ansLine:
    as = ai.rstrip(',').lstrip(',').split(',')
    if(len(as) > 10):
        ans[ax] = as
        am = am + 1
    ax = ax + 1
an = am
# And comparison
for i in range(ax):
    for j in range(ay):
        andMatrix[i][j] = (int)(test[i][j] & ans[i][j])
# Xor comparison
for i in range(ax):
    for j in range(ay):
        xorMatrix[i][j] = (int)(test[i][j] ^ ans[i][j])
# N/2 comparison
sx = ax / 2;
sy = ay / 2;
for i in range(sx):
    for j in range(ay):
        EastAndMatrix[i][j] = (int)test[i][j] & ans[i][j]
        EastXorMatrix[i][j] = (int)test[i][j] ^ ans[i][j]
for i in range(sx,ax):
    for j in range(ay):
        WestAndMatrix[i][j] = (int)test[i][j] & ans[i][j]
        WestXorMatrix[i][j] = (int)test[i][j] ^ ans[i][j]
for i in range(ax):
    for j in range(sy):
        NorthAndMatrix[i][j] = (int)test[i][j] & ans[i][j]
        NorthXorMatrix[i][j] = (int)test[i][j] ^ ans[i][j]
for i in range(ax):
    for j in range(sy,ay):
        SouthAndMatrix[i][j] = (int)test[i][j] & ans[i][j]
        SouthAndMatrix[i][j] = (int)test[i][j] ^ ans[i][j]
# Printing results
andSum = 0
xorSum = 0
eastAndSum = 0
eastXorSum = 0
westAndSum = 0
westXorSum = 0
northAndSum = 0
northXorSum = 0
southAndSum = 0
southXorSum = 0
for i in range(ax):
    for j in range(ay):
        andSum = andSum + andMatrix[i][j]
        xorSum = xorSum + xorMatrix[i][j]
        eastAnd = eastAnd + EastAndMatrix[i][j]
        westAnd = westAnd + WestAndMatrix[i][j]
        northAnd = northAnd + northAndMatrix[i][j]
        southAnd = southAnd + southAndMatrix[i][j]
        eastXor = eastXor + EastXorMatrix[i][j]
        westXor = westXor + WestXorMatrix[i][j]
        northXor = northXor + northXorMatrix[i][j]
        southXor = southXor + southXorMatrix[i][j]
print "And: "andSum
print "Xor: "xorSum
print "eastAnd: "eastAnd
print "westAnd: "westAnd
print "northAnd: "northAnd
print "southAnd: "southAnd
print "eastXor: "eastXor
print "westXor: "westXor
print "northXor: "northXor
print "southXor: "southXor

