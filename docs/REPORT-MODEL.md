In UReport2, cells show an dependency relationship to each other. You can set the left and top parent cell for any cell. The parent cell can be chosen by the user. By default, the left parent cell to a cell refers to the nearest-left cell in the same row; and the top parent cell is the nearest-upper cell in the same column. If a cell is located in the first row, it has no top parent cell by default; and if it is located in the first column, it has no left parent cell by default. Open the UReport2 report designer and select any cell. You can see its upper or left parent cell at its properties panel, as shown in the figure below:

![](docs/images/cell-parent.png)![](docs/images/cell-parent.png)![](docs/images/cell-parent.png)![](/docs/images/cell-parent.png)According to the above figure, we can manually change the left or top parent cell of the given cell.

If a cell is bound to a field within the data set and the field has more than one piece of data, the cell can be expanded to display the data. When a parent cell is expanded, the sub-cell will be expanded along. If the parent cell is expanded downward, its all sub-cells and all sub-cells to its sub-cells will be expanded downward. If the data set bound to the sub-cell is the same with the data set of the parent cell, the data in the sub-cell will be subject to restrictions of the parent cell. When the field data of the data set bound to the sub-cell is expanded, all sub-cells to the sub-cell are expanded at the same time. Wen the parent cell to the current sub-cell and the sub-cell are located in the same row or column, the parent cell will be widened.

Making use of above characteristics of iterated cells, we make prepare complex statements and reports in various styles. The prerequisite for development of reports is to master features of the report calculation model. See the detailed introduction of the report calculation model in the video class in this section. The video class describes the characteristics of iterated algorithm with many cases. The text here will not give more details.











