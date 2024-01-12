These files are samples of reated over my time working with Farmscan Pty Ltd.

The project uses an MSP430F247 with 32K of Flash and 4K of RAM. This was at 

the height of the global chip shortage so there weren't many options.

The majority of the files I have include here concern the GLCD/GUI side

of as I designed and built all of this aspect of the project. 


This GUI was not exactly trivial for me to implement. The low level sections

was easy as it's very simple hardware. I seperated the ks0108 driver layer into 

glcd_msp430_driver.c  

was happy with how it was progressing   

challenge was that the LCD was only 192x64 pixels and     


and was a lot more responsive 

than I was expecting it to be. I put this down to 2 factors, the hardware guy

knew to use a 6800 parallel connection, obviously parallel has the potential to 

be faster, but I wasn't expecting such a huge improvement over SPI as I assumed

the < $10 screen would be the bottleneck. That was a good learning experience.


The other thing that helped responsivness was my decision to seperate all 

screen drawing into seperate "create" and "update" states on all menu 

nodes.   



 

 

 

