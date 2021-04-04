# mbkz-sem-blackjack
 Semestralni prace na předmět KIV/MBKZ. Android aplikace - karetní hra Blackjack (Vlastní pravidla).
 
 # Informace
 Tato hra byla vyvynuta jako semestrální práce pro předmět KIV/MBKZ.
 Jedná se o hru, která má jádro v Blackjacku, avšak na pár vyjímek, kdy jsem si dovolil udělat vlastní zjednodušená pravidla. 
 
 # Pravidla
 ## Hodnoty karet
 Karty od dvojky do desítky (2 - 10) mají hodnotu uvedenou na kartě
 Karty J (jack), Q (dáma) a K (král) mají hodnotu 10
 Karta A (eso) má hodnotu 1 (zde je změna, hráč nemůže rozhodovat, jekou hodnotu bude mít karta)
 
 ## Cíl hry
 Princip hry je stejný a to ten, že hráč chce nashromáždit karty v hodnotě blíže celkovému součtu karet 21 než krupiér, ale nesmí překročit tuto hodnotu. 
 Hráč, jehož součet karet přesáhne 21. je vyřazen ze hry a prohrává.
 V případě stejného počtu bodů hráče a krupiéra je hra nerozhodná a nevyhrává nikdo. 
 
 ## Průběh hry
 Na začátku každé hry si musíte zvolit sázku a pak začne krupiér rozdávat karty.
 Na začátku každého kola krupiér rozdá hráči dvě karty a sobě dá jednu kartu.
 Po rozdání karet má hráč možnost 2 akcí:
    dostat další kartu (Hit),
    zůstat stát (Stand),
    další možnosti jako Split a Double nejsou možné.
    
## Krupiér a jeho hra
V normální Blackjacku má krupiér určitá pravidla jak hrát: 
    rozdat si další kartu, pokud je součet jeho karet menší než 17,
    zůstat stát ve chvíli, kdy je součet jeho karet 17 a vyšší.
Pro tuto hru, jsem se ale rozhodl pro vlastní algoritmus, který je založen na pravděpodobnosti a náhodě padnutí další karty.
    
 
 # TODO
 * Jádro hry ✔️
 * ReferenceActivity - udělat activity, ve které budou odkazy na autora
    * Webový odkaz
 * Sepsat pravidla hry
 * na Game over nebo vzdání hry, zapsat score - Scoreboard
    * Vymyset systém bodování
* Night mode - změna během runu aplikace ✔️
* Ošetření dealera pro i > 5
* Settings - pouze Number input
* ikona aplikace
* Pozadí v MENU
    * možná zelené pozadí ve hře
* zvuky do hry ✔️
* Přidat do appBaru zpět
* Algoritmus 
 
