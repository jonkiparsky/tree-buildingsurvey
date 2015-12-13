# Introduction #

TBS stores data about organisms in the file "organisms.properties". Facts about individual organisms can be changed. Some caution should be used if the number of organism changes, as there may be some internal reliance on the number 20 as the quantity.


# Details #

organism properties are structured as follows:

NAME = image\_filename, major grouping, minor grouping

where image\_filename is the name of a file in the bin/images directory. Major grouping is vertebrate/invertebrate, minor grouping currently only divides vertebrates into mammals and non-mammal vertebrates (NMV). Groupings are used in [tree analysis](Analysis.md).

TODO: find out what is needed to add minor grouping of inverts