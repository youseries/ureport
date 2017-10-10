# Overview

It is inevitable to apply functions and expressions to calculate some data during use of reports. Writing expressions are supported in many places in UReport2. Most typically, we can change the type of the cell to be “expression” and then we can enter the corresponding expression and function in the expression editor below. Moreover, we can also use expressions in conditions, picture sources and QR code data sources etc. in UReport2. The complex statements and reports can only be prepared after we learn and master expressions provided in UReport2.

# Basic syntax

Similar to general programming languages, expressions in UReport2 have some basic data types, such as number and string etc., as shown in the table below:

| Expression type | Description | Example |
| :--- | :--- | :--- |
| Number | It can be either an integer or a decimal | 1, 123 and 0.121331 are all legal numbers |
| String | The string shall be put in single quotes or double quotes | ‘ureport2’, "UReport2” and ‘UReport2 course’ are all legal strings |
| Boolean value | Boolean value represents Yes or No | There are two Boolean values: true & false |

The above three basic data types can be used independently or used in combinatorial calculation by connecting them with “+”, “-”, “\*”, "/” and “%”, as listed in the table below:

| Operators | Description | Example |
| :--- | :--- | :--- |
| + | Calculate the sum of two numbers or connect two values | 21+31 means to calculate the sum of the two numbers and the result should be 52. “value:”+331 means to connect the two values, and the result should be “value: 331” |
| - | Calculate the difference of two numbers | 21 – 31 indicates calculating the difference between the two numbers, and the result should be -10 |
| \* | Calculate the product of two numbers | 3\*6 results in 18 |
| / | Calculate the division of two numbers | 6/3 results in 2. If one number will not divide into the other number, 8 decimal places shall be kept |
| % | Calculate the residual value of two numbers | 5%3 results in 2; and 6%2 results in 0 |

UReport2 also provides several types of conditional judgement operators. The first is the ternary expression.

# Ternary expression

Almost all languages support the ternary expression judgement, which is clear and simple and makes conditional judgements based on the fewest codes. The syntactic structure of the ternary expression in UReport2 is shown in the figure below:

![](/docs/images/ternaryexpr.png)

According to the figure, it is same with ordinary ternary expressions that the first part is about the condition, which may consist of several conditions \(connect them with and or\). The part after “？” is the expression returned after execution when conditions are satisfied. The part after “：” is the expression returned after execution when conditions are not met.

| Examples of ternary expression | Note |
| :--- | :--- |
| A1&gt;1000 ? “normal value" : “low value" | For expression calculation, obtain the value of cell A1 first and judge whether the value is bigger than 1,000. If yes, return to the string of “normal value”, otherwise return to the string of “low value”. |
| A1&gt;1000 and A1&lt;20000 ? “normal value" : “corrected value："+\(A1+100\) | For the condition part, judge whether the value of A1 is greater than 1,000 and lesser than 20,000. If yes, return to the “normal value”, otherwise return to the value of the connection between the string “corrected value” and A1 value adding 100. If A1 is 2,000, then return to the “corrected value：2100” |

# If Expression

The syntactic structure of if expression in UReport2 is shown in the figure below:

![](/docs/images/ifexpr.png)According to the figure, if judgement expression consists of the part of if conditional judgement, the judgement with several alternative elseif conditions and finally the part of alternative else conditions. The syntactic structure is similar to java or javascript.

| Examples of if judgement expression | Note |
| :--- | :--- |
| if\(A1&gt;1000\){return "normal value"} | Judge whether the value of cell A1 is greater than 1,000. If yes, return to the string of “normal value”; and if no, do nothing. |
| if\(A1&gt;1000\){return "normal value"}else{“low value";} | Judge whether the value of cell A1 is greater than 1,000. If yes, return to the string of “normal value”, otherwise return to the string of “low value”. It shall be noted that in if expression, several options of the keyword of return are available. Adding ';’at the end of the row also indicates it is elective. This design is provided mainly to match the habits in java and javascript program. |
| if\(A1&gt;1000 and A1&lt;20000\){ return "normal value:"+A1}else if\(A&gt;20000 and A1&lt;40000\){ return "High value" }else if\(A&gt;20000 and A1&lt;40000\){ return "ultra-high value"}else{ “low value"} | In this case, several combined conditions are added in the part of conditions, and elseif demands multiple judgements. |

# Case Expression

Case expression is another form of conditional judgement provided by UReport2. It is similar to if judgement, but is simpler. Its syntactic structure is shown in the figure below:

![](/docs/images/caseexpr.png)

The case judgement should be put in case\{...}, followed by several conditions and the return value.

| Examples of case expression | Note |
| :--- | :--- |
| case{A1==100 return "normal ", A1&gt;100 and A1&lt;1000 return "normal value", A1&gt;100 and A1&lt;1000 return 'high'} | The two conditions have different values returned |
| case{A1==100 return "normal ", A1&gt;100 and A1&lt;1000 return "normal value", A1&gt;100 and A1&lt;1000  ‘high'} | There are also several options of the keyword of return in the case expression. |











