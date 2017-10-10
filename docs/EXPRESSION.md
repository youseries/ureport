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







