package com.example.funwithflags;


import java.util.ArrayList;

public class DataCreator {


    public static ArrayList<Country> create() {
        ArrayList<Country> countries = new ArrayList<>();
        ArrayList<Answer> myAnswers = new ArrayList<>();
        ArrayList<Question> myQuestions = new ArrayList<>();
        Country myCountry;
        Answer myAnswer;
        Question myQuestion;

        /* ***********************************************
         * Australia
         ***********************************************/
        // question 1
        myAnswer = new Answer("Sydney", false);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("Melbourne", false);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("Canberra", true);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("Darwin", false);
        myAnswers.add(myAnswer);

        myQuestion = new Question("What is the capital city of Australia?", myAnswers, 1, 5, 5, false);
        myQuestions.add(myQuestion);
        myAnswers = new ArrayList<>();

        // question 2
        myAnswer = new Answer("Uluru", false);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("Mt Kosciuszko", true);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("Mt St Helens", false);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("Mt Everest", false);
        myAnswers.add(myAnswer);

        myQuestion = new Question("What is the highest mountain in Australia?", myAnswers, 2, 10, 10, false);
        myQuestions.add(myQuestion);
        myAnswers = new ArrayList<>();

        // question 3
        myAnswer = new Answer("5 million", false);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("10.5 million", false);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("23 million", true);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("1 billion", false);
        myAnswers.add(myAnswer);

        myQuestion = new Question("What is the population of Australia", myAnswers, 3, 5, 5, false);
        myQuestions.add(myQuestion);
        myAnswers = new ArrayList<>();

        // question 4
        myAnswer = new Answer("Rose", false);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("Kangaroo Paw", false);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("Wattle", true);
        myAnswers.add(myAnswer);

        myQuestion = new Question("National floral emblem is?", myAnswers, 4, 15, 10, true);
        myQuestions.add(myQuestion);
        myAnswers = new ArrayList<>();

        // question 5
        myAnswer = new Answer("1939", false);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("1973", false);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("1902", true);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("1965", false);
        myAnswers.add(myAnswer);

        myQuestion = new Question("In what year did women receive the right to vote? ", myAnswers, 5, 10, 5, false);
        myQuestions.add(myQuestion);
        myAnswers = new ArrayList<>();

        // question 6
        myAnswer = new Answer("Death Adder", false);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("Tiger Snake", false);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("Taipan", true);
        myAnswers.add(myAnswer);

        myQuestion = new Question("What is the most venomous snake in Australia?", myAnswers, 6, 10, 10, false);
        myQuestions.add(myQuestion);
        myAnswers = new ArrayList<>();

        // question 7
        myAnswer = new Answer("2000", false);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("50,000", true);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("5000", false);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("70,000", false);
        myAnswers.add(myAnswer);

        myQuestion = new Question("How many years ago did Aboriginal people arrive in Australia?", myAnswers, 7, 10, 10, false);
        myQuestions.add(myQuestion);

        myCountry = new Country(R.drawable.flag_au, myQuestions, "Australia"); // australia
        countries.add(myCountry);

        myAnswers = new ArrayList<>();
        myQuestions = new ArrayList<>();

        /* ***********************************************
         * Belgium
         ***********************************************/
        // question 1
        myAnswer = new Answer("Namur", false);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("Brussels", true);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("Ghent", false);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("Antwerp", false);
        myAnswers.add(myAnswer);

        myQuestion = new Question("What is the largest city in Belgium?", myAnswers, 1, 5, 5, false);
        myQuestions.add(myQuestion);
        myAnswers = new ArrayList<>();

        // question 2
        myAnswer = new Answer("5", false);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("11", true);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("23", false);
        myAnswers.add(myAnswer);

        myQuestion = new Question("How many provinces in Belgium?", myAnswers, 2, 10, 10, false);
        myQuestions.add(myQuestion);
        myAnswers = new ArrayList<>();

        // question 3
        myAnswer = new Answer("German", false);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("French", false);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("Dutch", true);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("Spanish", false);
        myAnswers.add(myAnswer);

        myQuestion = new Question("What is the most spoken language in Belgium", myAnswers, 3, 5, 5, false);
        myQuestions.add(myQuestion);
        myAnswers = new ArrayList<>();

        // question 4
        myAnswer = new Answer("25.6 M", false);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("5.2 M", false);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("11.5 M", true);
        myAnswers.add(myAnswer);

        myQuestion = new Question("The population of Belgium is?", myAnswers, 4, 10, 10, false);
        myQuestions.add(myQuestion);
        myAnswers = new ArrayList<>();

        // question 5
        myAnswer = new Answer("True", true);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("False", false);
        myAnswers.add(myAnswer);

        myQuestion = new Question("Does Belgium Border France? ", myAnswers, 5, 5, 5, false);
        myQuestions.add(myQuestion);
        myAnswers = new ArrayList<>();

        // question 6
        myAnswer = new Answer("Hamburger", false);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("French fries", true);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("Tacos", false);
        myAnswers.add(myAnswer);

        myQuestion = new Question("What fast food originated in Belgium?", myAnswers, 6, 15, 10, true);
        myQuestions.add(myQuestion);
        myAnswers = new ArrayList<>();
        myCountry = new Country(R.drawable.flag_be, myQuestions, "Belgium"); // belgium
        countries.add(myCountry);

        myQuestions = new ArrayList<>();

        /* ***********************************************
         * Brazil
         ***********************************************/
        // question 1
        myAnswer = new Answer("Sao Paulo", false);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("Rio de Janero", false);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("Brazilia", true);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("Fortaliza", false);
        myAnswers.add(myAnswer);

        myQuestion = new Question("What is the capital city of Brazil?", myAnswers, 1, 5, 5, false);
        myQuestions.add(myQuestion);
        myAnswers = new ArrayList<>();

        // question 2
        myAnswer = new Answer("Congo", false);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("Daintree", false);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("The Amazon", true);
        myAnswers.add(myAnswer);

        myQuestion = new Question("The largest rainforest in Brazil is?", myAnswers, 2, 5, 5, false);
        myQuestions.add(myQuestion);
        myAnswers = new ArrayList<>();

        // question 3
        myAnswer = new Answer("Spanish", false);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("Brazilian", false);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("Portuguese", true);
        myAnswers.add(myAnswer);

        myQuestion = new Question("The main language in Brazil is?", myAnswers, 3, 5, 5, false);
        myQuestions.add(myQuestion);
        myAnswers = new ArrayList<>();

        // question 4
        myAnswer = new Answer("11.5 M", false);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("212 M", true);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("523.7 M", false);
        myAnswers.add(myAnswer);

        myQuestion = new Question("The population of Brazil is?", myAnswers, 4, 10, 10, false);
        myQuestions.add(myQuestion);
        myAnswers = new ArrayList<>();

        // question 5
        myAnswer = new Answer("Jaguar", true);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("Onca", false);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("Donkey", false);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("Llama", false);
        myAnswers.add(myAnswer);

        myQuestion = new Question("The national animal of Brazil is? ", myAnswers, 5, 15, 15, true);
        myQuestions.add(myQuestion);
        myAnswers = new ArrayList<>();

        // question 6
        myAnswer = new Answer("true", true);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("false", false);
        myAnswers.add(myAnswer);

        myQuestion = new Question("Brazil is the 5th most populated country in the world?", myAnswers, 6, 5, 5, false);
        myQuestions.add(myQuestion);
        myAnswers = new ArrayList<>();

        // question 7
        myAnswer = new Answer("Cancer", false);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("Capricorn", true);
        myAnswers.add(myAnswer);

        myQuestion = new Question("Which Tropic line runs through Brazil?", myAnswers, 7, 5, 5, false);
        myQuestions.add(myQuestion);
        myAnswers = new ArrayList<>();

        // question 8
        myAnswer = new Answer("Mt Amozonia", false);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("Pico de Neblina", true);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("Aqua de Coco", false);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("Mt Queijo", false);
        myAnswers.add(myAnswer);

        myQuestion = new Question("The highest mountain in Brazil is?", myAnswers, 8, 10, 10, true);
        myQuestions.add(myQuestion);
        myAnswers = new ArrayList<>();

        // question 9
        myAnswer = new Answer("Peso", false);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("Brazilian Dollar", false);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("Real", true);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("Lev", false);
        myAnswers.add(myAnswer);

        myQuestion = new Question("The national currency of Brazil is?", myAnswers, 9, 10, 10, false);
        myQuestions.add(myQuestion);
        myAnswers = new ArrayList<>();

        // question 10
        myAnswer = new Answer("true", false);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("false", true);
        myAnswers.add(myAnswer);

        myQuestion = new Question("Brazil borders Chile?", myAnswers, 10, 5, 5, false);
        myQuestions.add(myQuestion);
        myAnswers = new ArrayList<>();

        myCountry = new Country(R.drawable.flag_br, myQuestions, "Brazil"); // brazil
        countries.add(myCountry);

        myQuestions = new ArrayList<>();

        /* ***********************************************
         * China
         ***********************************************/

        // question 1
        myAnswer = new Answer("500 M", false);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("2.1 B", false);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("1.4 B", true);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("1.67 B", false);
        myAnswers.add(myAnswer);

        myQuestion = new Question("The population of China is?", myAnswers, 1, 5, 5, false);
        myQuestions.add(myQuestion);
        myAnswers = new ArrayList<>();

        // question 2
        myAnswer = new Answer("1785", false);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("1949", true);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("1981", false);
        myAnswers.add(myAnswer);

        myQuestion = new Question("China has been a communist country since?", myAnswers, 2, 5, 5, false);
        myQuestions.add(myQuestion);
        myAnswers = new ArrayList<>();

        // question 3
        myAnswer = new Answer("Beijing", false);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("Shandong", false);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("Shanghai", true);
        myAnswers.add(myAnswer);

        myQuestion = new Question("The largest city in China is?", myAnswers, 3, 5, 5, false);
        myQuestions.add(myQuestion);
        myAnswers = new ArrayList<>();

        // question 4
        myAnswer = new Answer("Yellow", false);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("Yangze", true);
        myAnswers.add(myAnswer);

        myQuestion = new Question("The longest river in China is?", myAnswers, 4, 5, 5, false);
        myQuestions.add(myQuestion);
        myAnswers = new ArrayList<>();

        // question 5
        myAnswer = new Answer("Left", false);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("Right", true);
        myAnswers.add(myAnswer);

        myQuestion = new Question("In China cars drive on the? ", myAnswers, 5, 10, 10, false);
        myQuestions.add(myQuestion);
        myAnswers = new ArrayList<>();

        myCountry = new Country(R.drawable.flag_cn, myQuestions, "China"); // china
        countries.add(myCountry);
        myQuestions = new ArrayList<>();

        /* ***********************************************
         * Germany
         ***********************************************/
        // question 1
        myAnswer = new Answer("Deutschmark", false);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("Euro", true);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("German Dollar", false);
        myAnswers.add(myAnswer);

        myQuestion = new Question("The currency in Germany is?", myAnswers, 1, 5, 5, false);
        myQuestions.add(myQuestion);
        myAnswers = new ArrayList<>();

        // question 2
        myAnswer = new Answer("1949", false);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("1989", true);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("2018", false);
        myAnswers.add(myAnswer);

        myQuestion = new Question("The Berlin Wall came down in?", myAnswers, 2, 10, 10, false);
        myQuestions.add(myQuestion);
        myAnswers = new ArrayList<>();

        // question 3
        myAnswer = new Answer("5", false);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("16", true);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("8", false);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("10", false);
        myAnswers.add(myAnswer);

        myQuestion = new Question("How many states in Germany?", myAnswers, 3, 10, 10, false);
        myQuestions.add(myQuestion);
        myAnswers = new ArrayList<>();

        // question 4
        myAnswer = new Answer("25.6 M", false);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("50.3 M", false);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("83 M", true);
        myAnswers.add(myAnswer);

        myQuestion = new Question("The population of Germany is?", myAnswers, 4, 10, 10, false);
        myQuestions.add(myQuestion);
        myAnswers = new ArrayList<>();

        // question 5
        myAnswer = new Answer("True", false);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("False", true);
        myAnswers.add(myAnswer);

        myQuestion = new Question("Does Germany Border Hungary? ", myAnswers, 5, 5, 5, false);
        myQuestions.add(myQuestion);
        myAnswers = new ArrayList<>();

        // question 6
        myAnswer = new Answer("80 L", false);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("110 L", true);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("50 L", true);
        myAnswers.add(myAnswer);

        myQuestion = new Question("The average beer consumption per person in Germany is?", myAnswers, 6, 15, 10, true);
        myQuestions.add(myQuestion);
        myCountry = new Country(R.drawable.flag_de, myQuestions, "Germany"); // germany
        countries.add(myCountry);

        myAnswers = new ArrayList<>();
        myQuestions = new ArrayList<>();

        /* ***********************************************
         * Spain
         ***********************************************/
        // question 1
        myAnswer = new Answer("Democracy", true);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("Oligarchy", false);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("Republic", false);
        myAnswers.add(myAnswer);

        myQuestion = new Question("Spain is a?", myAnswers, 1, 5, 5, false);
        myQuestions.add(myQuestion);
        myAnswers = new ArrayList<>();

        // question 2
        myAnswer = new Answer("45.6 M", false);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("47.4 M", true);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("70.2 M", false);
        myAnswers.add(myAnswer);

        myQuestion = new Question("The population of Spain is?", myAnswers, 2, 10, 10, false);
        myQuestions.add(myQuestion);
        myAnswers = new ArrayList<>();

        // question 3
        myAnswer = new Answer("Madrid", true);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("Barcelona", false);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("Catalonia", false);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("Valencia", false);
        myAnswers.add(myAnswer);

        myQuestion = new Question("The largest city in Spain is?", myAnswers, 3, 5, 5, false);
        myQuestions.add(myQuestion);
        myAnswers = new ArrayList<>();

        // question 4
        myAnswer = new Answer("Valencia", false);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("Pamplona", true);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("Bunol", false);
        myAnswers.add(myAnswer);

        myQuestion = new Question("The running of the bulls is held in?", myAnswers, 4, 15, 10, true);
        myQuestions.add(myQuestion);
        myAnswers = new ArrayList<>();

        // question 5
        myAnswer = new Answer("True", false);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("False", true);
        myAnswers.add(myAnswer);

        myQuestion = new Question("Does Spain Border Italy? ", myAnswers, 5, 5, 5, false);
        myQuestions.add(myQuestion);
        myAnswers = new ArrayList<>();

        // question 6
        myAnswer = new Answer("Coridillera Cantabica", false);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("Montes de Toledo", false);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("Mulhacen", true);
        myAnswers.add(myAnswer);

        myQuestion = new Question("The highest mountain in Spain is?", myAnswers, 6, 15, 10, false);
        myQuestions.add(myQuestion);
        myCountry = new Country(R.drawable.flag_es, myQuestions, "Spain"); // spain
        countries.add(myCountry);
        myAnswers = new ArrayList<>();
        myQuestions = new ArrayList<>();

        /* ***********************************************
         * france
         ***********************************************/
        // question 1
        myAnswer = new Answer("1956", false);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("1673", false);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("1789", true);
        myAnswers.add(myAnswer);

        myQuestion = new Question("The French revolution occurred in?", myAnswers, 1, 5, 5, false);
        myQuestions.add(myQuestion);
        myAnswers = new ArrayList<>();

        // question 2
        myAnswer = new Answer("Bordeaux", false);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("Versailles", false);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("Paris", true);
        myAnswers.add(myAnswer);

        myQuestion = new Question("The capitol city of France is?", myAnswers, 2, 5, 5, false);
        myQuestions.add(myQuestion);
        myAnswers = new ArrayList<>();

        // question 3
        myAnswer = new Answer("5", false);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("16", false);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("18", true);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("10", false);
        myAnswers.add(myAnswer);

        myQuestion = new Question("How many regions in France?", myAnswers, 3, 10, 10, false);
        myQuestions.add(myQuestion);
        myAnswers = new ArrayList<>();

        // question 4
        myAnswer = new Answer("Grande Tete de L'Obiou", false);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("Barre des Ecrins", false);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("Mont Blanc", true);
        myAnswers.add(myAnswer);

        myQuestion = new Question("The Highest mountain in France is?", myAnswers, 4, 10, 10, false);
        myQuestions.add(myQuestion);
        myAnswers = new ArrayList<>();

        // question 5
        myAnswer = new Answer("True", true);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("False", false);
        myAnswers.add(myAnswer);

        myQuestion = new Question("Does France border Spain? ", myAnswers, 5, 5, 5, false);
        myQuestions.add(myQuestion);

        myCountry = new Country(R.drawable.flag_fr, myQuestions, "France"); // france
        countries.add(myCountry);

        myAnswers = new ArrayList<>();
        myQuestions = new ArrayList<>();

        /* ***********************************************
         * England
         ***********************************************/
        // question 1
        myAnswer = new Answer("Sheffield", false);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("Nottingham", false);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("York", false);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("London", true);
        myAnswers.add(myAnswer);

        myQuestion = new Question("What is the capital city of England?", myAnswers, 1, 5, 5, false);
        myQuestions.add(myQuestion);
        myAnswers = new ArrayList<>();

        // question 2
        myAnswer = new Answer("Monarchy", true);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("Republic", false);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("Democracy", false);
        myAnswers.add(myAnswer);

        myQuestion = new Question("England is a?", myAnswers, 2, 5, 5, false);
        myQuestions.add(myQuestion);
        myAnswers = new ArrayList<>();

        // question 3
        myAnswer = new Answer("Euro", false);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("Dollar", false);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("Pound", true);
        myAnswers.add(myAnswer);

        myQuestion = new Question("The currency of England is?", myAnswers, 3, 5, 5, false);
        myQuestions.add(myQuestion);
        myAnswers = new ArrayList<>();

        // question 4
        myAnswer = new Answer("18 M", false);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("53 M", true);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("78 M", false);
        myAnswers.add(myAnswer);

        myQuestion = new Question("The population of England is?", myAnswers, 4, 10, 10, false);
        myQuestions.add(myQuestion);
        myAnswers = new ArrayList<>();

        // question 5
        myAnswer = new Answer("True", true);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("False", false);
        myAnswers.add(myAnswer);

        myQuestion = new Question("Chocolate was invented in England? ", myAnswers, 5, 15, 15, true);
        myQuestions.add(myQuestion);
        myAnswers = new ArrayList<>();

        // question 6
        myAnswer = new Answer("Paul", false);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("George", false);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("Peter", true);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("John", false);
        myAnswers.add(myAnswer);

        myQuestion = new Question("Which is not a member of the beatles?", myAnswers, 6, 10, 5, false);
        myQuestions.add(myQuestion);
        myAnswers = new ArrayList<>();

        // question 7
        myAnswer = new Answer("Cricket", false);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("Soccer", true);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("Rugby", false);
        myAnswers.add(myAnswer);

        myQuestion = new Question("What is the most popular sport in England?", myAnswers, 7, 5, 5, false);
        myQuestions.add(myQuestion);
        myAnswers = new ArrayList<>();

        // question 8
        myAnswer = new Answer("Daffodil", false);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("Violet", false);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("Rose", true);
        myAnswers.add(myAnswer);

        myQuestion = new Question("The national flower of England is?", myAnswers, 8, 10, 10, true);
        myQuestions.add(myQuestion);
        myAnswers = new ArrayList<>();

        // question 9
        myAnswer = new Answer("True", true);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("False", false);
        myAnswers.add(myAnswer);

        myQuestion = new Question("England borders Wales?", myAnswers, 9, 10, 10, false);
        myQuestions.add(myQuestion);
        myAnswers = new ArrayList<>();

        // question 10
        myAnswer = new Answer("Yellow", false);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("Black", true);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("Red", false);
        myAnswers.add(myAnswer);

        myQuestion = new Question("Taxis in England are?", myAnswers, 10, 5, 5, false);
        myQuestions.add(myQuestion);

        myCountry = new Country(R.drawable.flag_gb, myQuestions, "England"); // great britain
        countries.add(myCountry);

        myAnswers = new ArrayList<>();
        myQuestions = new ArrayList<>();


        /* ***********************************************
         * Georgia
         ***********************************************/

        // question 1
        myAnswer = new Answer("1905", false);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("1955", false);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("1859", false);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("1991", true);
        myAnswers.add(myAnswer);

        myQuestion = new Question("Georgia declared independence in?", myAnswers, 1, 15, 10, true);
        myQuestions.add(myQuestion);
        myAnswers = new ArrayList<>();

        // question 2
        myAnswer = new Answer("3.7 M", true);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("10.2 M", false);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("5.6 M", false);
        myAnswers.add(myAnswer);

        myQuestion = new Question("The population of Georgia is?", myAnswers, 2, 10, 10, false);
        myQuestions.add(myQuestion);
        myAnswers = new ArrayList<>();

        // question 3
        myAnswer = new Answer("Euro", false);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("Mark", false);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("Lari", true);
        myAnswers.add(myAnswer);

        myQuestion = new Question("The currency of Georgia is?", myAnswers, 3, 5, 5, false);
        myQuestions.add(myQuestion);
        myAnswers = new ArrayList<>();

        // question 4
        myAnswer = new Answer("Iran", false);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("Russia", true);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("Turkey", false);
        myAnswers.add(myAnswer);

        myQuestion = new Question("In 2008 Georgia went to war against?", myAnswers, 4, 10, 10, false);
        myQuestions.add(myQuestion);
        myAnswers = new ArrayList<>();

        // question 5
        myAnswer = new Answer("Black", true);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("Red", false);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("Mediterranean", false);
        myAnswers.add(myAnswer);

        myQuestion = new Question("Georgia borders the .... Sea? ", myAnswers, 5, 10, 10, false);
        myQuestions.add(myQuestion);

        myCountry = new Country(R.drawable.flag_ge, myQuestions, "Georgia"); // georgia
        countries.add(myCountry);
        myAnswers = new ArrayList<>();
        myQuestions = new ArrayList<>();


        /* ***********************************************
         * Hong Kong
         ***********************************************/

        // question 1
        myAnswer = new Answer("2001", false);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("1887", false);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("1997", true);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("1958", false);
        myAnswers.add(myAnswer);

        myQuestion = new Question("When was control of Hong Kong handed back to China?", myAnswers, 1, 10, 5, false);
        myQuestions.add(myQuestion);
        myAnswers = new ArrayList<>();

        // question 2
        myAnswer = new Answer("Yen", false);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("Hong Kong Dollar", true);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("Jiao", false);
        myAnswers.add(myAnswer);

        myQuestion = new Question("The currency in Hong Kong is the?", myAnswers, 2, 5, 5, false);
        myQuestions.add(myQuestion);
        myAnswers = new ArrayList<>();

        // question 3
        myAnswer = new Answer("15.7 M", false);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("50.2 M", false);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("7.5 M", true);
        myAnswers.add(myAnswer);

        myQuestion = new Question("The population of Hong Kong is?", myAnswers, 3, 5, 5, false);
        myQuestions.add(myQuestion);
        myAnswers = new ArrayList<>();

        // question 4
        myAnswer = new Answer("English", false);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("Portuguese", true);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("Spanish", false);
        myAnswers.add(myAnswer);

        myQuestion = new Question("The first Europeans to arrive in Honk Kong were?", myAnswers, 4, 10, 10, false);
        myQuestions.add(myQuestion);
        myAnswers = new ArrayList<>();

        // question 5
        myAnswer = new Answer("True", true);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("False", false);
        myAnswers.add(myAnswer);

        myQuestion = new Question("Hong Kong has the largest number of skyscrapers? ", myAnswers, 5, 5, 5, false);
        myQuestions.add(myQuestion);
        myAnswers = new ArrayList<>();

        // question 6
        myAnswer = new Answer("200 years", false);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("150 years", false);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("125 years", false);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("99 years", true);
        myAnswers.add(myAnswer);

        myQuestion = new Question("Britain leased Hong Kong for?", myAnswers, 6, 10, 5, false);
        myQuestions.add(myQuestion);


        myCountry = new Country(R.drawable.flag_hk, myQuestions, "Hong Kong"); // hongkong
        countries.add(myCountry);
        myAnswers = new ArrayList<>();
        myQuestions = new ArrayList<>();

        /* ***********************************************
         * Italy
         ***********************************************/
        // question 1
        myAnswer = new Answer("Monarchy", false);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("Oligarchy", false);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("Republic", true);
        myAnswers.add(myAnswer);

        myQuestion = new Question("Italy is a?", myAnswers, 1, 5, 5, false);
        myQuestions.add(myQuestion);
        myAnswers = new ArrayList<>();

        // question 2
        myAnswer = new Answer("Rome", true);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("Milan", false);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("Naples", false);
        myAnswers.add(myAnswer);

        myQuestion = new Question("The capital city of Italy is?", myAnswers, 2, 5, 5, false);
        myQuestions.add(myQuestion);
        myAnswers = new ArrayList<>();

        // question 3
        myAnswer = new Answer("Euro", true);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("Dollar", false);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("Lira", false);
        myAnswers.add(myAnswer);

        myQuestion = new Question("The currency of Italy is?", myAnswers, 3, 5, 5, false);
        myQuestions.add(myQuestion);
        myAnswers = new ArrayList<>();

        // question 4
        myAnswer = new Answer("60 M", true);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("53 M", false);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("78 M", false);
        myAnswers.add(myAnswer);

        myQuestion = new Question("The population of Italy is?", myAnswers, 4, 10, 10, false);
        myQuestions.add(myQuestion);
        myAnswers = new ArrayList<>();

        // question 5
        myAnswer = new Answer("Axis", true);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("Allies", false);
        myAnswers.add(myAnswer);

        myQuestion = new Question("During WW2 Italy was on the side of? ", myAnswers, 5, 15, 15, true);
        myQuestions.add(myQuestion);
        myAnswers = new ArrayList<>();


        // question 6
        myAnswer = new Answer("1", false);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("2", false);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("3", false);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("4", true);
        myAnswers.add(myAnswer);

        myQuestion = new Question("Italy is surrounded by how many seas? ", myAnswers, 6, 15, 15, true);
        myQuestions.add(myQuestion);
        myCountry = new Country(R.drawable.flag_it, myQuestions, "Italy"); // italy
        countries.add(myCountry);
        myAnswers = new ArrayList<>();
        myQuestions = new ArrayList<>();


        /* ***********************************************
         * Japan
         ***********************************************/


        // question 1
        myAnswer = new Answer("Republic", false);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("Monarchy", true);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("Oligarchy", false);
        myAnswers.add(myAnswer);

        myQuestion = new Question("Japan is a?", myAnswers, 1, 5, 5, false);
        myQuestions.add(myQuestion);
        myAnswers = new ArrayList<>();

        // question 2
        myAnswer = new Answer("126 M", true);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("300 M", false);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("80 M", false);
        myAnswers.add(myAnswer);

        myQuestion = new Question("The population of Japan is?", myAnswers, 2, 5, 5, false);
        myQuestions.add(myQuestion);
        myAnswers = new ArrayList<>();

        // question 3
        myAnswer = new Answer("Jiao", false);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("Dollar", false);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("Yen", true);
        myAnswers.add(myAnswer);

        myQuestion = new Question("The currency of Japan is?", myAnswers, 3, 5, 5, false);
        myQuestions.add(myQuestion);
        myAnswers = new ArrayList<>();

        // question 4
        myAnswer = new Answer("2", false);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("4", true);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("6", false);
        myAnswers.add(myAnswer);

        myQuestion = new Question("How many main islands are in Japan?", myAnswers, 4, 10, 10, false);
        myQuestions.add(myQuestion);
        myAnswers = new ArrayList<>();

        // question 5
        myAnswer = new Answer("Sumo", true);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("Karate", false);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("Judo", false);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("Kendo", false);
        myAnswers.add(myAnswer);

        myQuestion = new Question("Japans national sport is? ", myAnswers, 5, 15, 15, true);
        myQuestions.add(myQuestion);

        myCountry = new Country(R.drawable.flag_jp, myQuestions, "Japan"); // japan
        countries.add(myCountry);

        myAnswers = new ArrayList<>();
        myQuestions = new ArrayList<>();

        /* ***********************************************
         * Lithuania
         ***********************************************/

        // question 1
        myAnswer = new Answer("Asia", false);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("Africa", false);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("South America", false);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("Europe", true);
        myAnswers.add(myAnswer);

        myQuestion = new Question("Lithuania is on the continent of?", myAnswers, 1, 5, 5, false);
        myQuestions.add(myQuestion);
        myAnswers = new ArrayList<>();

        // question 2
        myAnswer = new Answer("Monarchy", false);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("Republic", true);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("Democracy", false);
        myAnswers.add(myAnswer);

        myQuestion = new Question("Lithuania is a?", myAnswers, 2, 5, 5, false);
        myQuestions.add(myQuestion);
        myAnswers = new ArrayList<>();

        // question 3
        myAnswer = new Answer("Euro", false);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("Lithuanian Dollar", true);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("Lira", false);
        myAnswers.add(myAnswer);

        myQuestion = new Question("The currency of Lithuania is?", myAnswers, 3, 5, 5, false);
        myQuestions.add(myQuestion);
        myAnswers = new ArrayList<>();

        // question 4
        myAnswer = new Answer("8.4 M", false);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("5.2 M", false);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("2.7 M", true);
        myAnswers.add(myAnswer);

        myQuestion = new Question("The population of Lithuania is?", myAnswers, 4, 10, 10, false);
        myQuestions.add(myQuestion);
        myAnswers = new ArrayList<>();

        // question 5
        myAnswer = new Answer("Left",  false);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("Right", true);
        myAnswers.add(myAnswer);

        myQuestion = new Question("In Lithuania cars drive on the? ", myAnswers, 5, 5, 5, false);
        myQuestions.add(myQuestion);
        myAnswers = new ArrayList<>();

        // question 6
        myAnswer = new Answer("5", false);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("15", false);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("10", true);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("13", false);
        myAnswers.add(myAnswer);

        myQuestion = new Question("How many divisions are in Lithuania?", myAnswers, 6, 10, 5, false);
        myQuestions.add(myQuestion);

        myCountry = new Country(R.drawable.flag_lt, myQuestions, "Lithuania"); // lithuania
        countries.add(myCountry);

        myAnswers = new ArrayList<>();
        myQuestions = new ArrayList<>();

        /* ***********************************************
         * Russia
         ***********************************************/

        // question 1
        myAnswer = new Answer("1886", false);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("1932", false);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("1917", true);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("1942", false);
        myAnswers.add(myAnswer);

        myQuestion = new Question("The Russian revolution occurred in?", myAnswers, 1, 5, 5, false);
        myQuestions.add(myQuestion);
        myAnswers = new ArrayList<>();

        // question 2
        myAnswer = new Answer("Moscow", true);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("Saint Petersburg", false);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("Novosibirsk", false);
        myAnswers.add(myAnswer);

        myQuestion = new Question("The most populated city in Russia is?", myAnswers, 2, 5, 5, false);
        myQuestions.add(myQuestion);
        myAnswers = new ArrayList<>();

        // question 3
        myAnswer = new Answer("Euro", false);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("Lipa", false);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("Ruble", true);
        myAnswers.add(myAnswer);

        myQuestion = new Question("The currency of Russia is?", myAnswers, 3, 5, 5, false);
        myQuestions.add(myQuestion);
        myAnswers = new ArrayList<>();

        // question 4
        myAnswer = new Answer("148 M", true);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("48 M", false);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("248 M", false);
        myAnswers.add(myAnswer);

        myQuestion = new Question("The population of Russia is?", myAnswers, 4, 10, 10, false);
        myQuestions.add(myQuestion);
        myAnswers = new ArrayList<>();

        // question 5
        myAnswer = new Answer("True", true);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("False", false);
        myAnswers.add(myAnswer);

        myQuestion = new Question("The first man is space was Russian? ", myAnswers, 5, 5, 5, false);
        myQuestions.add(myQuestion);
        myAnswers = new ArrayList<>();

        // question 6
        myAnswer = new Answer("Sputnik", false);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("ISS", false);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("Mir", true);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("Soyuz", false);
        myAnswers.add(myAnswer);

        myQuestion = new Question("The First Russian Space station was named?", myAnswers, 6, 15, 10, true);
        myQuestions.add(myQuestion);

        myCountry = new Country(R.drawable.flag_ru, myQuestions, "Russia"); // russia
        countries.add(myCountry);

        myAnswers = new ArrayList<>();
        myQuestions = new ArrayList<>();

        /* ***********************************************
         * USA
         ***********************************************/

        // question 1
        myAnswer = new Answer("New York", false);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("Los Angeles", false);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("Houston", false);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("Washington", true);
        myAnswers.add(myAnswer);

        myQuestion = new Question("What is the capital city of the USA?", myAnswers, 1, 5, 5, false);
        myQuestions.add(myQuestion);
        myAnswers = new ArrayList<>();

        // question 2
        myAnswer = new Answer("1964", false);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("1968", false);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("1969", true);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("1971", false);
        myAnswers.add(myAnswer);

        myQuestion = new Question("The USA landed a man on the moon in?", myAnswers, 2, 5, 5, false);
        myQuestions.add(myQuestion);
        myAnswers = new ArrayList<>();

        // question 3
        myAnswer = new Answer("Roswell", false);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("Dallas", true);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("New York", false);
        myAnswers.add(myAnswer);

        myQuestion = new Question("President Kennedy was shot in?", myAnswers, 3, 5, 5, false);
        myQuestions.add(myQuestion);
        myAnswers = new ArrayList<>();

        // question 4
        myAnswer = new Answer("330 M", true);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("530 M", false);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("430 M", false);
        myAnswers.add(myAnswer);

        myQuestion = new Question("The population of the USA is?", myAnswers, 4, 10, 10, false);
        myQuestions.add(myQuestion);
        myAnswers = new ArrayList<>();

        // question 5
        myAnswer = new Answer("True", false);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("False", true);
        myAnswers.add(myAnswer);

        myQuestion = new Question("The declaration of independence was signed in 1775? ", myAnswers, 5, 15, 15, true);
        myQuestions.add(myQuestion);
        myAnswers = new ArrayList<>();

        // question 6
        myAnswer = new Answer("40", false);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("60", false);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("50", true);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("30", false);
        myAnswers.add(myAnswer);

        myQuestion = new Question("How many states in the USA?", myAnswers, 6, 10, 5, false);
        myQuestions.add(myQuestion);
        myAnswers = new ArrayList<>();

        // question 7
        myAnswer = new Answer("Baseball", false);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("Ice Hockey", false);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("Basketball", false);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("Football", true);
        myAnswers.add(myAnswer);

        myQuestion = new Question("The most popular sport in the USA is?", myAnswers, 7, 10, 5, false);
        myQuestions.add(myQuestion);


        myCountry = new Country(R.drawable.flag_us, myQuestions, "USA"); // usa
        countries.add(myCountry);

        myAnswers = new ArrayList<>();
        myQuestions = new ArrayList<>();

        /* ***********************************************
         * Denmark
         ***********************************************/

        // question 1
        myAnswer = new Answer("Helsinki", false);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("Copenhagen", true);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("Luxembourg", false);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("Stockholm", false);
        myAnswers.add(myAnswer);

        myQuestion = new Question("What is the capital city of Denmank?", myAnswers, 1, 5, 5, false);
        myQuestions.add(myQuestion);
        myAnswers = new ArrayList<>();

        // question 2
        myAnswer = new Answer("Monarchy", true);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("Republic", false);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("Oligarchy", false);
        myAnswers.add(myAnswer);

        myQuestion = new Question("Denmark is a?", myAnswers, 2, 5, 5, false);
        myQuestions.add(myQuestion);
        myAnswers = new ArrayList<>();

        // question 3
        myAnswer = new Answer("Euro", false);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("Dollar", false);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("Krone", true);
        myAnswers.add(myAnswer);

        myQuestion = new Question("The currency of Denmark is?", myAnswers, 3, 5, 5, false);
        myQuestions.add(myQuestion);
        myAnswers = new ArrayList<>();

        // question 4
        myAnswer = new Answer("5.8 M", true);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("50.2 M", false);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("7.8 M", false);
        myAnswers.add(myAnswer);

        myQuestion = new Question("The population of Denmark is?", myAnswers, 4, 10, 10, false);
        myQuestions.add(myQuestion);
        myAnswers = new ArrayList<>();

        // question 5
        myAnswer = new Answer("True", true);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("False", false);
        myAnswers.add(myAnswer);

        myQuestion = new Question("Lego was invented in Denmark? ", myAnswers, 5, 10, 10, false);
        myQuestions.add(myQuestion);
        myAnswers = new ArrayList<>();

        // question 6
        myAnswer = new Answer("Nordjylland", false);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("Syddanmark", false);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("Hovedstaden", false);
        myAnswers.add(myAnswer);
        myAnswer = new Answer("Midtjylland", true);
        myAnswers.add(myAnswer);

        myQuestion = new Question("The largest region in Denmark is?", myAnswers, 6, 15, 5, true);
        myQuestions.add(myQuestion);

        myCountry = new Country(R.drawable.flag_dk, myQuestions, "Denmark"); // denmark
        countries.add(myCountry);

        return countries;
    }

}
