package com.example.data

import android.util.Log

object GaroBibleDataSeeder {

    suspend fun seedDatabase(dao: BibleDao) {
        val count = dao.getVerseCount()
        if (count > 0) {
            Log.d("GaroBibleDataSeeder", "Database already seeded with $count verses.")
            return
        }

        Log.d("GaroBibleDataSeeder", "Seeding books metadata...")
        val books = listOf(
            // Old Testament
            Book(1, "A·bachenga", "Genesis", "Old", 50),
            Book(2, "Ong·gata", "Exodus", "Old", 40),
            Book(3, "Leviticus", "Leviticus", "Old", 27),
            Book(4, "Ganna", "Numbers", "Old", 36),
            Book(5, "Niam Gipin", "Deuteronomy", "Old", 34),
            Book(6, "Joshua", "Joshua", "Old", 24),
            Book(7, "Bichar Ka·giparang", "Judges", "Old", 21),
            Book(8, "Rut", "Ruth", "Old", 4),
            Book(9, "1 Samuel", "1 Samuel", "Old", 31),
            Book(10, "2 Samuel", "2 Samuel", "Old", 24),
            Book(11, "1 Noalkong", "1 Kings", "Old", 22),
            Book(12, "2 Noalkong", "2 Kings", "Old", 25),
            Book(13, "1 Itihas", "1 Chronicles", "Old", 29),
            Book(14, "2 Itihas", "2 Chronicles", "Old", 36),
            Book(15, "Ezra", "Ezra", "Old", 10),
            Book(16, "Nehemia", "Nehemiah", "Old", 13),
            Book(17, "Ester", "Esther", "Old", 10),
            Book(18, "Job", "Job", "Old", 42),
            Book(19, "Git", "Psalms", "Old", 150),
            Book(20, "Toe Skianirang", "Proverbs", "Old", 31),
            Book(21, "Sastroni Skianirang", "Ecclesiastes", "Old", 12),
            Book(22, "Gitni Git", "Song of Solomon", "Old", 8),
            Book(23, "Isaiah", "Isaiah", "Old", 66),
            Book(24, "Jeremia", "Jeremiah", "Old", 52),
            Book(25, "Jeremiani Kal·gipa Git", "Lamentations", "Old", 5),
            Book(26, "Ezekiel", "Ezekiel", "Old", 48),
            Book(27, "Daniel", "Daniel", "Old", 12),
            Book(28, "Hosea", "Hosea", "Old", 14),
            Book(29, "Joel", "Joel", "Old", 3),
            Book(30, "Amos", "Amos", "Old", 9),
            Book(31, "Obadia", "Obadiah", "Old", 1),
            Book(32, "Jona", "Jonah", "Old", 4),
            Book(33, "Mika", "Micah", "Old", 7),
            Book(34, "Nahum", "Nahum", "Old", 3),
            Book(35, "Habakuk", "Habakkuk", "Old", 3),
            Book(36, "Zepania", "Zephaniah", "Old", 3),
            Book(37, "Hagai", "Haggai", "Old", 2),
            Book(38, "Zekaria", "Zechariah", "Old", 14),
            Book(39, "Malaki", "Malachi", "Old", 4),

            // New Testament
            Book(40, "Mati", "Matthew", "New", 28),
            Book(41, "Mark", "Mark", "New", 16),
            Book(42, "Luka", "Luke", "New", 24),
            Book(43, "Johan", "John", "New", 21),
            Book(44, "Kamrang", "Acts", "New", 28),
            Book(45, "Romi", "Romans", "New", 16),
            Book(46, "1 Korinti", "1 Corinthians", "New", 16),
            Book(47, "2 Korinti", "2 Corinthians", "New", 13),
            Book(48, "Galatia", "Galatians", "New", 6),
            Book(49, "Ephesian", "Ephesians", "New", 6),
            Book(50, "Philippian", "Philippians", "New", 4),
            Book(51, "Kolose", "Colossians", "New", 4),
            Book(52, "1 Thessalonian", "1 Thessalonian", "New", 5),
            Book(53, "2 Thessalonian", "2 Thessalonian", "New", 3),
            Book(54, "1 Timoti", "1 Timothy", "New", 6),
            Book(55, "2 Timoti", "2 Timothy", "New", 4),
            Book(56, "Tito", "Titus", "New", 3),
            Book(57, "Philemon", "Philemon", "New", 1),
            Book(58, "Hebri", "Hebrews", "New", 13),
            Book(59, "Jakob", "James", "New", 5),
            Book(60, "1 Pitar", "1 Peter", "New", 5),
            Book(61, "2 Pitar", "2 Peter", "New", 3),
            Book(62, "1 Johan", "1 John", "New", 5),
            Book(63, "2 Johan", "2 John", "New", 1),
            Book(64, "3 Johan", "3 John", "New", 1),
            Book(65, "Juda", "Jude", "New", 1),
            Book(66, "Parape·anirang", "Revelation", "New", 22)
        )
        dao.insertBooks(books)

        Log.d("GaroBibleDataSeeder", "Seeding curated offline verses...")
        val verses = mutableListOf<Verse>()

        // 1. Genesis (A·bachenga) Chapter 1
        val b1 = 1
        val b1Name = "A·bachenga"
        verses.add(Verse("${b1}_1_1", b1, b1Name, 1, 1, "A·bachengao Isol salgi aro a·gilsakko on·bachengaha."))
        verses.add(Verse("${b1}_1_2", b1, b1Name, 1, 2, "A·gilsak bimang gri aro bangbang ong·achim; aro tona bilonggipa simgipa nola chi·gilsakni mikkangko king·dugipa gimikni kosako Isolni Gisim rongtalgipa mo·jolengachim."))
        verses.add(Verse("${b1}_1_3", b1, b1Name, 1, 3, "Uandake Isol, ‘Seng·a ong·bo,’ inaha; unon seng·a ong·aha."))
        verses.add(Verse("${b1}_1_4", b1, b1Name, 1, 4, "Isol seng·ako nion, namgipa ong·achim; unon Isol seng·ako andaloni dingtanggataha."))
        verses.add(Verse("${b1}_1_5", b1, b1Name, 1, 5, "Isol seng·ako ‘Salo,’ aro andalako ‘Walo’ mingaha. Aro attam ong·aha aro pring ong·aha, uan skanggipa sal."))
        verses.add(Verse("${b1}_1_6", b1, b1Name, 1, 6, "Isol inaha, ‘Chirangni jatchio salgi daka donga daka·bo, uan chirangko chirangoni dingtanggatbo.’"))
        verses.add(Verse("${b1}_1_7", b1, b1Name, 1, 7, "Indake Isol salgi daka gita dakaha; aro daka gita uni ka·maoni chirangko aro salgi daka gita uni kosako chirangko dingtanggataha, unon indake ong·aha."))
        verses.add(Verse("${b1}_1_8", b1, b1Name, 1, 8, "Isol salgi daka gitako ‘Salgi’ mingaha. Aro attam ong·aha aro pring ong·aha, uan gnigipa sal."))
        verses.add(Verse("${b1}_1_9", b1, b1Name, 1, 9, "Isol inaha, ‘Salgi ka·maoni chirang daldaka biapona tom·dakbo, aro ran·gipa a·a nikbo,’ inaha; unon indake ong·aha."))
        verses.add(Verse("${b1}_1_10", b1, b1Name, 1, 10, "Isol ran·gipa a·ako ‘A·a’ aro chirang tom·dak gitako ‘Chi·gilsak’ mingaha; Isol uko nion, namgipa ong·achim."))

        // 19. Psalms (Git) Chapter 23
        val b19 = 19
        val b19Name = "Git"
        verses.add(Verse("${b19}_23_1", b19, b19Name, 23, 1, "Jihova angni bika·gipa ong·a; angna mamung bobil dongsrangjana."))
        verses.add(Verse("${b19}_23_2", b19, b19Name, 23, 2, "Ua angko ran·gipa samsi nanggipa samsirango tuata; aro tom·tomanini chirangni rikamona dilanga."))
        verses.add(Verse("${b19}_23_3", b19, b19Name, 23, 3, "Ua angni janggiko tang·gitalata; aro an·tangni biming gimin angko rongtalgipa dila ramao dila."))
        verses.add(Verse("${b19}_23_4", b19, b19Name, 23, 4, "Bebe, andalgipa jo·chong ramako re·genoba, angna mamung gol·gipin dongjana; maina na·a ang baksa donga; na·a angko taltongako dakgipa bilko on·a."))
        verses.add(Verse("${b19}_23_5", b19, b19Name, 23, 5, "Na·a angni mikkango ang bobilrangni mikkango mikkron daka tesa mesa; na·a ang knilko to baksa noko; angni kapa gaprangen gataha."))
        verses.add(Verse("${b19}_23_6", b19, b19Name, 23, 6, "Chong·mot, ang janggini salrang gimikna namgipa aro ka·saani ang baksa ja·rikgen; aro anga jringjrotna Jihovani noko donggen."))

        // 19. Psalms (Git) Chapter 100
        verses.add(Verse("${b19}_100_1", b19, b19Name, 100, 1, "Salgini manderang gimik, Jihovana kusi ong·e manderang bimikbo!"))
        verses.add(Verse("${b19}_100_2", b19, b19Name, 100, 2, "Kusi ong·aniko dake Jihovana ka·sae dakbo; git ring·e Uni mikkangona re·babo."))
        verses.add(Verse("${b19}_100_3", b19, b19Name, 100, 3, "Uianibo, Jihova uan Isol ong·a; Uan an·tangna chinga dakaha, aro chinga Uni manderang aro Uni matchangni mesrang ong·a."))
        verses.add(Verse("${b19}_100_4", b19, b19Name, 100, 4, "Mitela baksa Uni cholgugirangona, aro de·mitela baksa Uni cholgugirangona chipbo; Unona re·bade mitelbo, aro Uni bimingko rongtalgipa dakbo!"))
        verses.add(Verse("${b19}_100_5", b19, b19Name, 100, 5, "Maina Jihova namgipa ong·a; Uni kema ka·ani jringjrotna donga, aro Uni bebe ong·ani chiring-chiring donga."))

        // 20. Proverbs (Toe Skianirang) Chapter 3 (selected)
        val b20 = 20
        val b20Name = "Toe Skianirang"
        verses.add(Verse("${b20}_3_5", b20, b20Name, 3, 5, "Na·tangni uianina ka·donggija, gimik ka·tong baksa Jihovana ka·dongbo."))
        verses.add(Verse("${b20}_3_6", b20, b20Name, 3, 6, "Na·ni re·gipa rama gimiko Uko uichakbo, unon Ua na·ni re·ani ramarangko name dakatgen."))
        verses.add(Verse("${b20}_3_7", b20, b20Name, 3, 7, "An·tangko uigipa gita chanchigija, Jihovana kengipa ong·bo, aro gital ong·gijaniko galbo."))

        // 40. Matthew (Mati) Chapter 5 (Beatitudes)
        val b40 = 40
        val b40Name = "Mati"
        verses.add(Verse("${b40}_5_1", b40, b40Name, 5, 1, "Manderang tom·dake dongako nion, Ua a·briona doangaha; aro Uni asongon, Uni chattrirang Uona re·baha."))
        verses.add(Verse("${b40}_5_2", b40, b40Name, 5, 2, "Unon Ua an·tang ku·sikko oeba uarangna skion inaha:"))
        verses.add(Verse("${b40}_5_3", b40, b40Name, 5, 3, "‘Ka·tongni kangal ong·giparang kusi ong·giparang ong·a, maina salgini songnok uarangni ong·a.’"))
        verses.add(Verse("${b40}_5_4", b40, b40Name, 5, 4, "‘Kal·gipa manderang kusi ong·giparang ong·a, maina uarango tom·tomgipa donggen.’"))
        verses.add(Verse("${b40}_5_5", b40, b40Name, 5, 5, "‘Rongtalgipa manderang kusi ong·giparang ong·a, maina uarang a·gilsakko man·rikgipa ong·gen.’"))
        verses.add(Verse("${b40}_5_6", b40, b40Name, 5, 6, "‘Kaketgipa kusi ong·giparang kusi ong·giparang ong·a, maina uarang okkapgipa ong·gen.’"))
        verses.add(Verse("${b40}_5_7", b40, b40Name, 5, 7, "‘Ka·sae dakgipa manderang kusi ong·giparang ong·a, maina uarangkoba ka·sagen.’"))
        verses.add(Verse("${b40}_5_8", b40, b40Name, 5, 8, "‘Ka·tong rongtalgipa manderang kusi ong·giparang ong·a, maina uarang Isolko nikgen.’"))
        verses.add(Verse("${b40}_5_9", b40, b40Name, 5, 9, "‘Tom·tomaniko dakgiparang kusi ong·giparang ong·a, maina uarangko Isolni de·drang minggen.’"))
        verses.add(Verse("${b40}_5_10", b40, b40Name, 5, 10, "‘Kaket gimin so·selako man·giparang kusi ong·giparang ong·a, maina salgini songnok uarangni ong·a.’"))
        verses.add(Verse("${b40}_5_11", b40, b40Name, 5, 11, "‘Manderang anga gimin na·simangko so·selon, so·sel dingtang dingtangna bebera·on, na·simang kusi ong·gen.’"))
        verses.add(Verse("${b40}_5_12", b40, b40Name, 5, 12, "‘Kusi ong·bo aro a·gilsakni de·mittela dake kusi ong·bo, maina salgio na·simangni boksis chong·mot donga.’"))

        // 43. John (Johan) Chapter 1
        val b43 = 43
        val b43Name = "Johan"
        verses.add(Verse("${b43}_1_1", b43, b43Name, 1, 1, "A·bachenggaba Kattara donga; aro Kattara Isol baksa donga, aro Kattara Isol chong·mot ong·achim."))
        verses.add(Verse("${b43}_1_2", b43, b43Name, 1, 2, "Uan a·bachenggaba Isol baksa donga."))
        verses.add(Verse("${b43}_1_3", b43, b43Name, 1, 3, "Uan gimikkon dakaha; aro dakgiparangoni mamungba Uona gri daka dongjachim."))
        verses.add(Verse("${b43}_1_4", b43, b43Name, 1, 4, "Uona janggi donga, aro janggi manderangni seng·a ong·achim."))
        verses.add(Verse("${b43}_1_5", b43, b43Name, 1, 5, "Seng·a andalo seng·jolenga, aro andal uko amajachim."))
        verses.add(Verse("${b43}_1_6", b43, b43Name, 1, 6, "Isoloni watatgipa manderang donga, uni biming Johan."))
        verses.add(Verse("${b43}_1_7", b43, b43Name, 1, 7, "Ua sakki gita, seng·ani gimin sakki on·na, uni gimin gimikan uona bebera·na man·en gita re·baha."))
        verses.add(Verse("${b43}_1_8", b43, b43Name, 1, 8, "Ua seng·a chong·mot ong·jachim, indiba seng·ani gimin sakki on·na gipasan ong·achim."))
        verses.add(Verse("${b43}_1_9", b43, b43Name, 1, 9, "A·gilsakona re·bae mande sakantiko seng·atgipa, seng·a chong·mot uan donga."))
        verses.add(Verse("${b43}_1_10", b43, b43Name, 1, 10, "Ua a·gilsako donga, aro a·gilsak Uni daka ong·genoba, a·gilsak Uko uijachim."))
        verses.add(Verse("${b43}_1_11", b43, b43Name, 1, 11, "An·tangonona Ua re·baha, aro Uni an·tang manderangba Uko ra·chakjachim."))
        verses.add(Verse("${b43}_1_12", b43, b43Name, 1, 12, "Indiba Uko ra·chakgipa sakantina, Uni bimingna bebera·giparangna, Isolni de·drang ong·na bilko Ua on·aha."))
        verses.add(Verse("${b43}_1_13", b43, b43Name, 1, 13, "Uarang an·chi ba be·enni namgipa gimin ba mandeni namgipa gimin ba chong·mot ba·giparang ong·ja, indiba Isolonin ba·giparang ong·a."))
        verses.add(Verse("${b43}_1_14", b43, b43Name, 1, 14, "Aro Kattara be·en dake rongtalan baksa chinga jatchio dongaha, (aro pring dake de·mittela gipa, Babaoni on·gipa Depantgipani de·mittela gita, chingba Uni rasongko nikaha,) ka·sae daka aro bebe gipin gapgipa ong·achim."))

        // 43. John (Johan) Chapter 3
        verses.add(Verse("${b43}_3_1", b43, b43Name, 3, 1, "Perisirangoni saksa donga, uni biming Nikodemas, ua Juderangni dal·gipa ong·achim;"))
        verses.add(Verse("${b43}_3_2", b43, b43Name, 3, 2, "ua walona Uona re·bae Uona inaha, ‘Rabbi, na·a Isoloni re·babae skigipa ong·a chinga uia; maina na·a dakgipa niorangko, Isol uni baksa dongjaode, darangba dakna amja.’"))
        verses.add(Verse("${b43}_3_3", b43, b43Name, 3, 3, "Isol dake uni mikkango bobil daka, ‘Bebe chong·mot, anga na·na in-a: mandeko gital ba·baode, ua Isolni songnokko nikna amja.’"))
        verses.add(Verse("${b43}_3_14", b43, b43Name, 3, 14, "Aro Mose ramgipo chipuko chika gita de·mittela, uandake Mandeni Depantgipakoba de·mitela nanggen;"))
        verses.add(Verse("${b43}_3_15", b43, b43Name, 3, 15, "uona bebera·gipa sakanti jringjrotni janggiko man·na gipa gita."))
        verses.add(Verse("${b43}_3_16", b43, b43Name, 3, 16, "Maina Isol a·gilsakna uandake ka·saha, uandake Uni sak sangipa Depantgipako on·aha, uona bebera·gipa sakanti gimangjana, indiba jringjrotni janggiko man·na gipa."))
        verses.add(Verse("${b43}_3_17", b43, b43Name, 3, 17, "Maina Isol a·gilsakko so·selna gita Uni Depantgipako watatjachim, indiba a·gilsak Uona jokatgipa ong·na gipasa."))
        verses.add(Verse("${b43}_3_18", b43, b43Name, 3, 18, "Uona bebera·gipade বিচারona re·baja; indiba bebera·gipa gitade jringjrotni a·bachonge বিচার daka ong·genoba, maina ua Isolni sak sangipa Depantgipani bimingna bebera·jachim."))

        // 45. Romans (Romi) Chapter 12 (selected)
        val b45 = 45
        val b45Name = "Romi"
        verses.add(Verse("${b45}_12_1", b45, b45Name, 12, 1, "Uni gimin, an·tangtangni be·enrangko Isolna rongtalgipa aro Uni ra·chake donggipa janggi on·gipa dake on·bo."))
        verses.add(Verse("${b45}_12_2", b45, b45Name, 12, 2, "Aro a·gilsakni bimingona dingtanggija, an·tangtangni gisikrangko tang·gitalate biming dingtangbo, indon Isolni ra·chakgipa aro namgipa niamko na·simang nirokna man·gen."))
        verses.add(Verse("${b45}_12_9", b45, b45Name, 12, 9, "Ka·saanide chal dakkagija ong·bo. Namgijanina mitchibo, namgipa baksa ma·grikbo."))
        verses.add(Verse("${b45}_12_12", b45, b45Name, 12, 12, "Ka·dongona kusi ong·bo, jajrengona jringe chakbo, bi·onade jringe dake donga donga-bo."))
        verses.add(Verse("${b45}_12_21", b45, b45Name, 12, 21, "Namgijana amgipa ong·gija, namgipa baksa namgijako ambo."))

        dao.insertVerses(verses)
        Log.d("GaroBibleDataSeeder", "Successfully seeded ${verses.size} verses.")

        // Also seed some initial devotionals to make sure they are available right away!
        val dates = listOf("2026-07-05", "2026-07-06", "2026-07-07")
        val dev1 = Devotional(
            id = 1,
            date = "2026-07-05",
            bookName = "Git",
            chapterNumber = 23,
            verseNumber = 1,
            verseText = "Jihova angni bika·gipa ong·a; angna mamung bobil dongsrangjana.",
            title = "Jihova Angni Nirakgipa",
            devotionalText = "Da·alni a·gilsako janga tangani biapo bang·a so·selarang aro jajrenganirang donga. Indiba David Git-o to skiani on·a: Jihova chong·mot angni bika·gipa ong·a. Ua chingko niroka, rakkia aro ramako dila. Mamung jajrenganiko Isolna on·bo.",
            prayer = "Gisikni rongtalgipa Isol, na·a angni Nirakgipa ong·ani gimin mitela. Angni gisiko tom·tomaniko on·bo aro da·al ang baksa re·bo. Amen."
        )
        val dev2 = Devotional(
            id = 2,
            date = "2026-07-06",
            bookName = "Johan",
            chapterNumber = 3,
            verseNumber = 16,
            verseText = "Maina Isol a·gilsakna uandake ka·saha, uandake Uni sak sangipa Depantgipako on·aha, uona bebera·gipa sakanti gimangjana, indiba jringjrotni janggiko man·na gipa.",
            title = "Isolni Dal·gipa Ka·saani",
            devotionalText = "Isolni ka·saani manderangna dal·chongmotgipa boksis ong·a. Ua an·tangni Depantgipako on·aha, uona bebera·gipa sakanti jringjrotni janggiko man·gen gita. Bebera·ani baksa chingni ka·tongko gapatbo.",
            prayer = "Ka·sae dakgipa Baba Isol, na·ni Depantgipa Jisuko watatani gimin chinga mitela. Chingna jringjrotni janggiko on·gipa Bebera·aniko ronge baksana rakkibo. Amen."
        )
        dao.insertDevotional(dev1)
        dao.insertDevotional(dev2)
    }
}
