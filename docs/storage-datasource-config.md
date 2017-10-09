# ![](docs/images/save-dialog.png)Reports Storage

In section 2, we have introduced how to create a project incorporating UReport2. After the project starts running, we will find a directory named “urepor tfiles” automatically generated under the WEB-INF directory of the project. This is the default directory of storage of report files provided by UReport2. In other words, the report files designed will be saved under the WEB-INF/ureportfiles directory of the project by UReport by default.

If the project runs in an Eclipse development environment, jetty \(such as the run-jetty-run of eclipse plug-in\) is applied. Then we will see a directory named “ureportfiles” under the WEB-INF directory of the project.

If the project runs based on tomcat, there will be no directory of “ureportfiles” under the WEB-INF directory, because tomcat needs to create a temporary working directory when tomcat is operated in Eclipse \(the directory is generally located under workspace.metadata.plugins\org.eclipse.wst.server.core\tmp0\wtpwebapp\). Therefore, when the project runs based on tomcat, it is necessary to find the corresponding project under the temporary working directory and then find the corresponding “ureportfiles” directory under the WEB-INF directory of the project.

Start running the project created, open the report designer, click the save button on the toolbar and select “save”. Then we can see the pop-up window as shown in the figure below:![](docs/images/save-dialog.png)

![](docs/images/save-dialog.png)![](docs/images/save-dialog.png)![](docs/images/save-dialog.png)

