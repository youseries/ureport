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

The case judgement should be put in case{...}, followed by several conditions and the return value.

| Examples of case expression | Note |
| :--- | :--- |
| case{A1==100 return "normal ", A1&gt;100 and A1&lt;1000 return "normal value", A1&gt;100 and A1&lt;1000 return 'high'} | The two conditions have different values returned |
| case{A1==100 return "normal ", A1&gt;100 and A1&lt;1000 return "normal value", A1&gt;100 and A1&lt;1000  ‘high'} | There are also several options of the keyword of return in the case expression. |

# Cell reference

Currently, most calculations in reports are made against cells or are relevant to cells. Since cells in reports are mostly bound to data and data are often multiple, one cell may have multiple values after calculation, making cell reference more complicated. In UReport2, the target cell referred to is calculated relative to the current cell. The name of the cell can be directly written in the expression for cell reference. For example, if referring to cell A1, directly write A1 in the expression. See the example below:

![](/docs/images/s1.png)

We enter the expression A1 in the cell D1 in the above figure means flling in the cell D1 with the value of cell A1 relative to the current cell D1. Then, the result will be:

![](/docs/images/s1-runtime.png)

Since D1 is the sub-cell to A1, the data that cell A1 is bound to featues the sectional structure. Based on the location of the current cell D1, the result shown in the figure above is reached. If entering B1 in cell D1, the result will be:

![](/docs/images/s1-runtime1.png)

Similarly, if entering expression C1 in D1, it will fill in each D1 cell with the value of C1 cell that is located in the same row of D1 cell. The result is not given here.

According to the above example, if referring to the target cell with an expression in a cell in UReport2, the first thing is to judge whether the target cell is located in the same row of column with this cell. If yes, the value of the target cell at the corresponding row or column can be directly applied. If the current cell and the target cell are not in the sam row or column, the situation will be different. Let’s see another example.

![](/docs/images/s2.png)

In the above example, we enter B1 in the expression of cell C2 to indicate the reference to the value of cell B1. The cell B1 and C2 are not in the same row or column, and cell B1 will have several values after being expanded. However, both cell B1 and cell C2 have a common parent cell or indirect parent cell A1 \(the left parent cell to cell C2 is B2, and the left parent cell to cell B2 is A1, so A1 is the indirect left parent cell to cell C2\), hence all values of B1 under the common parent cell A1 will be applied. The result is shown in the figure below:

![](/docs/images/s2-runtime.png)

Outputs of several values：If more than one value is acquired in UReport2, these values shall be separated with “,” for outputs, as shown in the figure above.

Principle for acquisition of the target cell ：As indicated in the above example, when acquiring the value of the target cell through the expression in a cell in UReport2, the first thing to consider is whether the target cell is located in the same row or column with the current cell. If yes, apply the value of the target cell located in the same row or column. If no, apply all target cells that have a common parent cell with the current cell. If they have a common top parent cell or common left parent cell, apply the target cell in the intersction between the common top parent cell and common left parent cell; and if they do not have a common parent cell, then all target cells after iteration shall be applied.

If we enter C1 in the above cell in the above example, the result will be different. Since C1 is the top parent cell to C2, we directly use the value of the top parent cell located in the same column. The result is shown in the following figure:

![](/docs/images/s2-runtime1.png)

Now, let’s look at an example of report:

![](/docs/images/s3.png)

In the example above, we enter C1 in the expression of cell B2. Since B2 and C2 are not in the same row or column and have no common parent cell, B2 will take all values of cell C1, as shown in the following figure:

![](/docs/images/s3-runtime.png)

Change the parent cell to get the value of a cell：When introducing the report calculation model in the previous video class, we have changed the upper or left parent cell to the current cell several times to realize a common parent cell between the current cell and the target cell. The principle is given here.

# Cell coordinates

UReport2 introduces the concept of cell coordinates in order to realize the more complicated cell reference. The cell coordinates are also calculated relative to the current cell. The principle of giving priority the same row, same column or common parent cell introduced above shall also apply here. A standard form of cell coordinates shall be as follows:

**CellName\[Li:li,Li-1:li-1,…;Ti:ti,Ti-1:ti-1…\]   
{condition...}  
**

L represents the left parent cell and l is the sequence number after the left parent cell is expanded; if the sequence number is a negative value, it means moving upward. T represents the top parent cell and t is the sequence number after the left parent cell is expanded; if the sequence number is a negative value, it means moving upward relative to the current cell, and if the number is a positive value, it means moving downward. If it only has a left parent cell, directly write the L part; and if it only has a top parent cell, place “;” before it and write the T part. The part of condition is placed in braces, where conditions shall be connected with and/or if more than one condition, to represent condition screening over the cells acquired through coordinates \(if more than one cell are acquired\). Opinions are available for the condition part. See the example as below:

| Examples of cell coordinates | Note |
| :--- | :--- |
| C1\[A1:2,B1:1\] | When looking for C1, firstly find the second cell after cell A1 is expanded, then find the first cell after the second cell B1 under A1 is expanded, and finally find the cell C1 corresponding to cell B1. |
| C2\[A1:2,B1:2;C1:3\] | When looking for C2, firstly find the second cell after cell A1 is expanded, then find the second cell after the second cell B2 under A1 is expanded, find the left sub-cell known as cell C2 under the second cell B2 expanded, find the third cell after cell C1 is expanded and finally find the cell C2 under it and take the intersection of cell C2. |
| C2\[A1:2,B2:2\]{C2&gt;1000} | It means to take the second cell after cell A2 is expanded, then take the second cell after cell B2 under the cell taken above is expanded, take all C2 cells under B2, filter the conditions for cell C2 acquired and only take all C2 cells where the value of cell C2 exceeds 1,000. |
| C2\[A1:2,B2:2\]{C2&gt;1000 and C2&lt;10000} | It means to take the second cell after cell A2 is expanded, then take the second cell after cell B2 under the cell taken above is expanded, take all C2 cells under B2, filter the conditions for cell C2 acquired and only take all C2 cells where the value of cell C2 exceeds 1,000 but is smaller than 10,000. |

Let’s look at a concrete example. The report template is shown below:

![](/docs/images/s4.png)

In the above report template, we enter C1\[A1:2,B1:1\] in the expression of cell B2, which means to take the value of the corresponding cell C1 in the first cell after expansion of B1 which is in the second cell after cell A1 is expanded, so the result is shown in the following figure:

![](/docs/images/s4-runtime.png)

Of course, we can prepare a crosstab and locate a certain cell by adding the coordinates of its left and top parent cell. It will not be further demonstrated here. In practical applications, cell coordinates can be used in statistical statements on year-on-year or month-on-month basis. The sequence number of cell coordinates may be negative to suggest moving the current cell, in order to calculate ratios here.

# Refer to all cells

If we need to refer to all cells, we only need to add ”\[\]“ after the name of the cell. In case of A1\\[\], it means to refer to all A1 cells no matter where the referred cell is located. Conditions shall be used when referring to all cells, thus to realize conditional restriction on referred cells. For example, **A1\[\]{A1&gt;1000 and A1&lt;10000} **means to refer to all A1 cells, but the value of A1 cells referred to shall be greater than 1,000 and less than 10,000.

# Month-on-month basis

Report template is shown below:

![](/docs/images/s5.png)

In the above report template, the expression in cell D2 is C2 - C2\\[A2:-1\], which means to firstly take the value of cell C2 in cell D2, because cell C2 is located in the same row with D2 and is the only cell that can be directly acquired. The next C2 adopts the coordinates A2:-1, indicating to take the cell C2 corresponding to the cell A2 that is one cell top upon the cell A2 relative to the current cell \(the negative value suggests moving upward\). The result is shown in the following figure:

![](/docs/images/s5-runtime.png)

