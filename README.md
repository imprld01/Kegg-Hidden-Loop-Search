# Kegg-Hidden-Loop-Search

Hidden regulatory relationship for a loop on gene regulation networks  
are not completely revealed for most of biological pathway database.  
Hence, we hope to construct a program that can find the hidden loops back.

This project is an example to use the library, Feedback_Loop_Search.jar, on searching kegg hidden loop.  
In repository, you can find many main files, representing each stage in a complete searching experiment.  
You can read ExecutionMain.java to know more about the process of the experiment.  
The library applies kegg database as the main resource for searching hidden loops.  
Hoping this can help you using library to write your own program, according to your needs.

If wanting to search hidden loops on kegg database instantly, you can find the program [below](https://goo.gl/DfRmzj).  
If interested in the methodology of searching hidden loops on kegg database, you can find doc [here]().

If there is any problem, you can send email to [me](mailto:sbw%32%3319@g%6D%61il.%63%6F%6D), or go [here](https://sayat.me/tosbw2319) to leave an anonymous message to me.  
If using the anonymous system, you should tell me which repository you are talking about.

Thanks a lot! ☺️

# KGI

The searching is based on KGI, an integrated data structure parsed from [kegg](http://www.kegg.jp) database in specific species.

* Find the KGI file immediately under **res** directory on this repository
  * **KEGG Orthology**
    1. Org Code: *ko*
    2. find the KGI file [here](https://goo.gl/ldhIra)
    3. retrieved from [KEGG](http://www.kegg.jp) on 2017/05/07
    4. place *Kgml_Info.ki* in specific directory: ~/Database/Kgml_Information/ko/
  * **Danio rerio (zebrafish)**
    1. Org Code: [*dre*](http://www.genome.jp/kegg-bin/show_organism?org=dre)
    2. find the KGI file [here](https://goo.gl/Y57ulx)
    3. retrieved from [KEGG](http://www.kegg.jp) on 2017/06/09
    4. place *Kgml_Info.ki* in specific directory: ~/Database/Kgml_Information/dre/
  * **Homo sapiens (human)**
    1. Org Code: [*hsa*](http://www.genome.jp/kegg-bin/show_organism?org=hsa)
    2. find the KGI file [here]()
    3. retrieved from [KEGG](http://www.kegg.jp) on 2017/06/09
    4. place *Kgml_Info.ki* in specific directory: ~/Database/Kgml_Information/hsa/

# Archive File

* [Here](https://goo.gl/IT45ib) you can find the archive file for this project
* [Here](https://goo.gl/Hkso37) you can find what is inside the archive file
  * [Here](https://goo.gl/PMFctb) you can find the user guide

# Library Document

[Here](https://goo.gl/B8amn6) you can find the java document for *Feedback_Loop_Search.jar*.
