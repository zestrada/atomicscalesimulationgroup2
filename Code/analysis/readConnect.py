#/usr/python
def str2bol( val ):
    tmp = (val.find("true") != -1)
    return tmp

# Testing
testFile = open("connection.dat")
testLines = (testFile.read()).replace('[','').replace('],',']').rstrip(',')
testLine = testLines.split(']')
tn = len(testLine)
tm = 0
atest = [[ 0 for i in range(tn-2)] for j in range(tn-2)]
test = [[ (1==0) for i in range(tn-2)] for j in range(tn-2)]
tx = 0
for ti in testLine:
    ts = ti.rstrip(',').lstrip(',').split(',')
    if(len(ts) > 10):
        atest[tx] = ts
        tm = tm + 1
    tx = tx + 1
tn = tm
# Answer
ansFile = open("answer.dat")
ansLines = (ansFile.read()).replace('[','').replace('],',']').rstrip(',')
ansLine = ansLines.split(']')
an = len(testLine)
am = 0
aans = [[ 0 for i in range(an-2)] for j in range(an-2)]
ans = [[ (1==0) for i in range(an-2)] for j in range(an-2)]
ax = 0
for ai in ansLine:
    ass = ai.rstrip(',').lstrip(',').split(',')
    if(len(ass) > 10):
        aans[ax] = ass
        am = am + 1
    ax = ax + 1
an = am
# Matrix initialization
andMatrix = [[ 0 for i in range(an-2)] for j in range(an-2)]
xorMatrix = [[ 0 for i in range(an-2)] for j in range(an-2)]
EastAndMatrix = [[ 0 for i in range(an-2)] for j in range(an-2)]
WestAndMatrix = [[ 0 for i in range(an-2)] for j in range(an-2)]
NorthAndMatrix = [[ 0 for i in range(an-2)] for j in range(an-2)]
SouthAndMatrix = [[ 0 for i in range(an-2)] for j in range(an-2)]
EastXorMatrix = [[ 0 for i in range(an-2)] for j in range(an-2)]
WestXorMatrix = [[ 0 for i in range(an-2)] for j in range(an-2)]
NorthXorMatrix = [[ 0 for i in range(an-2)] for j in range(an-2)]
SouthXorMatrix = [[ 0 for i in range(an-2)] for j in range(an-2)]
# String conversion
for i in range(an-2):
    for j in range(an-2):
        test[i][j] = str2bol(atest[i][j].lower())
        ans[i][j] = str2bol(aans[i][j].lower())
# And comparison
for i in range(an-2):
    for j in range(an-2):
        andMatrix[i][j] = int(test[i][j] & ans[i][j])
# Xor comparison
for i in range(an-2):
    for j in range(an-2):
        xorMatrix[i][j] = int(test[i][j] ^ ans[i][j])
# N/2 comparison
sx = (an-2) / 2;
for i in range(sx):
    for j in range(an-2):
        EastAndMatrix[i][j] = int(test[i][j] & ans[i][j])
        EastXorMatrix[i][j] = int(test[i][j] ^ ans[i][j])
for i in range(sx,an-2):
    for j in range(an-2):
        WestAndMatrix[i][j] = int(test[i][j] & ans[i][j])
        WestXorMatrix[i][j] = int(test[i][j] ^ ans[i][j])
for i in range(an-2):
    for j in range(sx):
        NorthAndMatrix[i][j] = int(test[i][j] & ans[i][j])
        NorthXorMatrix[i][j] = int(test[i][j] ^ ans[i][j])
for i in range(an-2):
    for j in range(sx,an-2):
        SouthAndMatrix[i][j] = int(test[i][j] & ans[i][j])
        SouthXorMatrix[i][j] = int(test[i][j] ^ ans[i][j])
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
scale = 1.0/((an-2)*(an-2))
for i in range(an-2):
    for j in range(an-2):
        andSum = andSum + andMatrix[i][j]
        xorSum = xorSum + xorMatrix[i][j]
        eastAndSum = eastAndSum + EastAndMatrix[i][j]
        westAndSum = westAndSum + WestAndMatrix[i][j]
        northAndSum = northAndSum + NorthAndMatrix[i][j]
        southAndSum = southAndSum + SouthAndMatrix[i][j]
        eastXorSum = eastXorSum + EastXorMatrix[i][j]
        westXorSum = westXorSum + WestXorMatrix[i][j]
        northXorSum = northXorSum + NorthXorMatrix[i][j]
        southXorSum = southXorSum + SouthXorMatrix[i][j]
print "And: ",andSum,andSum*scale
print "Xor: ",xorSum,xorSum*scale
print "eastAnd: ",eastAndSum,eastAndSum*scale
print "westAnd: ",westAndSum,westAndSum*scale
print "northAnd: ",northAndSum,northAndSum*scale
print "southAnd: ",southAndSum,southAndSum*scale
print "eastXor: ",eastXorSum,eastXorSum*scale
print "westXor: ",westXorSum,westXorSum*scale
print "northXor: ",northXorSum,northXorSum*scale
print "southXor: ",southXorSum,southXorSum*scale

