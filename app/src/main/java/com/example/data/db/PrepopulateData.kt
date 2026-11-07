package com.example.data.db

import com.example.data.model.BloodDonor
import com.example.data.model.DoctorInfo
import com.example.data.model.EducationalInstitute
import com.example.data.model.EmergencyContact
import com.example.data.model.KrishiAdvice
import com.example.data.model.MarketMember
import com.example.data.model.NoticeItem
import com.example.data.model.ParishadOfficial
import com.example.data.model.ShopInfo
import com.example.data.model.UserSubmission
import com.example.data.model.VillageInfo

object PrepopulateData {

    val emergencyContacts = listOf(
        EmergencyContact(
            title = "সানন্দবাড়ী পুলিশ তদন্ত কেন্দ্র",
            subtitle = "আইনশৃঙ্খলা রক্ষা ও তাৎক্ষণিক নিরাপত্তা",
            category = "police",
            phoneNumber = "01713374201",
            availableHours = "২৪ ঘণ্টা চালু",
            location = "সানন্দবাড়ী বাজার সংলগ্ন, দেওয়ানগঞ্জ"
        ),
        EmergencyContact(
            title = "দেওয়ানগঞ্জ মডেল থানা",
            subtitle = "ডিউটি অফিসার ও জরুরি পুলিশ সহায়তা",
            category = "police",
            phoneNumber = "01713374200",
            availableHours = "২৪ ঘণ্টা চালু",
            location = "দেওয়ানগঞ্জ, জামালপুর"
        ),
        EmergencyContact(
            title = "সানন্দবাড়ী অ্যাম্বুলেন্স সেবা",
            subtitle = "জরুরি রোগী পরিবহন ও অক্সিজেন সহায়তা",
            category = "ambulance",
            phoneNumber = "01711223344",
            availableHours = "২৪ ঘণ্টা চালু",
            location = "সানন্দবাড়ী কেন্দ্রীয় মোড়"
        ),
        EmergencyContact(
            title = "সানন্দবাড়ী ইউনিয়ন স্বাস্থ্য ও প.ক. কেন্দ্র",
            subtitle = "জরুরি প্রাথমিক চিকিৎসা ও প্রসূতি সেবা",
            category = "hospital",
            phoneNumber = "01720114455",
            availableHours = "সকাল ৮টা - বিকাল ৪টা (জরুরি সার্বক্ষণিক)",
            location = "চর আমখাওয়া ইউনিয়ন স্বাস্থ্য কেন্দ্র"
        ),
        EmergencyContact(
            title = "দেওয়ানগঞ্জ ফায়ার সার্ভিস স্টেশন",
            subtitle = "অগ্নিনির্বাপণ ও নদী দুর্যোগ উদ্ধার সেবা",
            category = "fire",
            phoneNumber = "01714556677",
            availableHours = "২৪ ঘণ্টা চালু",
            location = "দেওয়ানগঞ্জ ফায়ার স্টেশন"
        ),
        EmergencyContact(
            title = "পল্লী বিদ্যুৎ অভিযোগ কেন্দ্র (সানন্দবাড়ী)",
            subtitle = "বিদ্যুৎ বিভ্রাট ও ট্রান্সফরমার মেরামত",
            category = "electricity",
            phoneNumber = "01715667788",
            availableHours = "সকাল ৮টা - রাত ১০টা",
            location = "সানন্দবাড়ী সাব-স্টেশন এলাকা"
        ),
        EmergencyContact(
            title = "চর আমখাওয়া ইউনিয়ন পরিষদ চেয়ারম্যান",
            subtitle = "প্রশাসনিক সেবা ও নাগরিক প্রত্যয়ন",
            category = "parishad",
            phoneNumber = "01712345678",
            availableHours = "সকাল ৯টা - বিকাল ৫টা",
            location = "ইউনিয়ন পরিষদ কমপ্লেক্স, সানন্দবাড়ী"
        ),
        EmergencyContact(
            title = "জাতীয় জরুরি সেবা (৯৯৯)",
            subtitle = "পুলিশ, অ্যাম্বুলেন্স ও ফায়ার সার্ভিস (টোল ফ্রি)",
            category = "ambulance",
            phoneNumber = "999",
            availableHours = "২৪ ঘণ্টা চালু",
            location = "বাংলাদেশ সরকার"
        )
    )

    val educationalInstitutes = listOf(
        EducationalInstitute(
            name = "সানন্দবাড়ী ডিগ্রি কলেজ",
            type = "কলেজ",
            headName = "অধ্যক্ষ: জনাব মোঃ সিরাজুল ইসলাম",
            establishedYear = "১৯৮৮",
            villageOrLocation = "সানন্দবাড়ী কলেজ রোড, ১নং ওয়ার্ড",
            phoneNumber = "01712987654",
            totalStudentsOrInfo = "ডিগ্রি ও এইচএসসি শাখা (বিজ্ঞান, মানবিক, ব্যবসায় শিক্ষা)"
        ),
        EducationalInstitute(
            name = "সানন্দবাড়ী বহুমুখী উচ্চ বিদ্যালয়",
            type = "উচ্চ মাধ্যমিক / মাধ্যমিক বিদ্যালয়",
            headName = "প্রধান শিক্ষক: জনাব মোঃ আশরাফুল আলম",
            establishedYear = "১৯৬৪",
            villageOrLocation = "সানন্দবাড়ী বাজার সংলগ্ন",
            phoneNumber = "01715876543",
            totalStudentsOrInfo = "ঐতিহ্যবাহী প্রাচীন বিদ্যাপীঠ, ছাত্র-ছাত্রী: ১২০০+"
        ),
        EducationalInstitute(
            name = "সানন্দবাড়ী বালিকা উচ্চ বিদ্যালয়",
            type = "উচ্চ মাধ্যমিক / মাধ্যমিক বিদ্যালয়",
            headName = "প্রধান শিক্ষক: মোছাঃ শামীমা নাসরিন",
            establishedYear = "১৯৯৪",
            villageOrLocation = "সানন্দবাড়ী পূর্বপাড়া",
            phoneNumber = "01718765432",
            totalStudentsOrInfo = "নারী শিক্ষায় বিশিষ্ট প্রতিষ্ঠান, ষষ্ঠ থেকে দশম শ্রেণি"
        ),
        EducationalInstitute(
            name = "সানন্দবাড়ী সিনিয়র আলিম মাদ্রাসা",
            type = "মাদ্রাসা",
            headName = "অধ্যক্ষ / সুপার: মাওলানা মোঃ আব্দুল কাদের",
            establishedYear = "১৯৭৮",
            villageOrLocation = "সানন্দবাড়ী মাদ্রাসা মোড়",
            phoneNumber = "01721654321",
            totalStudentsOrInfo = "দাখিল ও আলিম স্তরের দ্বীনি শিক্ষাপ্রতিষ্ঠান"
        ),
        EducationalInstitute(
            name = "চর আমখাওয়া উচ্চ বিদ্যালয়",
            type = "উচ্চ মাধ্যমিক / মাধ্যমিক বিদ্যালয়",
            headName = "প্রধান শিক্ষক: জনাব মোঃ কামরুল হাসান",
            establishedYear = "১৯৯৮",
            villageOrLocation = "চর আমখাওয়া গ্রাম, ২নং ওয়ার্ড",
            phoneNumber = "01723543210",
            totalStudentsOrInfo = "মাধ্যমিক স্তরের বিদ্যালয়"
        ),
        EducationalInstitute(
            name = "সানন্দবাড়ী সরকারি প্রাথমিক বিদ্যালয়",
            type = "প্রাথমিক বিদ্যালয়",
            headName = "প্রধান শিক্ষক: জনাব মোঃ জহুরুল ইসলাম",
            establishedYear = "১৯৩৯",
            villageOrLocation = "সানন্দবাড়ী, ১নং ওয়ার্ড",
            phoneNumber = "01726432109",
            totalStudentsOrInfo = "প্রাচীন সরকারি প্রাথমিক বিদ্যালয়"
        ),
        EducationalInstitute(
            name = "চর আমখাওয়া সরকারি প্রাথমিক বিদ্যালয়",
            type = "প্রাথমিক বিদ্যালয়",
            headName = "প্রধান শিক্ষক: মোছাঃ সালমা খাতুন",
            establishedYear = "১৯৭৩",
            villageOrLocation = "চর আমখাওয়া, ২নং ওয়ার্ড",
            phoneNumber = "01728321098",
            totalStudentsOrInfo = "শিশু শ্রেণি থেকে পঞ্চম শ্রেণি"
        ),
        EducationalInstitute(
            name = "পাথরের চর সরকারি প্রাথমিক বিদ্যালয়",
            type = "প্রাথমিক বিদ্যালয়",
            headName = "প্রধান শিক্ষক: জনাব মোঃ মজিবুর রহমান",
            establishedYear = "১৯৮৫",
            villageOrLocation = "পাথরের চর, ৩নং ওয়ার্ড",
            phoneNumber = "01730210987",
            totalStudentsOrInfo = "চরের শিক্ষার্থীদের নির্ভরযোগ্য প্রাথমিক বিদ্যালয়"
        ),
        EducationalInstitute(
            name = "লম্বাপাড়া সরকারি প্রাথমিক বিদ্যালয়",
            type = "প্রাথমিক বিদ্যালয়",
            headName = "প্রধান শিক্ষক: জনাব মোঃ দেলোয়ার হোসেন",
            establishedYear = "১৯৮০",
            villageOrLocation = "লম্বাপাড়া, ৪নং ওয়ার্ড",
            phoneNumber = "01732109876",
            totalStudentsOrInfo = "সরকারি প্রাথমিক বিদ্যালয়"
        ),
        EducationalInstitute(
            name = "বয়ড়াপাড়া সরকারি প্রাথমিক বিদ্যালয়",
            type = "প্রাথমিক বিদ্যালয়",
            headName = "প্রধান শিক্ষক: মোছাঃ রাবেয়া বসরী",
            establishedYear = "১৯৮৮",
            villageOrLocation = "বয়ড়াপাড়া, ৫নং ওয়ার্ড",
            phoneNumber = "01734098765",
            totalStudentsOrInfo = "শিশু থেকে পঞ্চম শ্রেণি"
        ),
        EducationalInstitute(
            name = "ঝালোরচর সরকারি প্রাথমিক বিদ্যালয়",
            type = "প্রাথমিক বিদ্যালয়",
            headName = "প্রধান শিক্ষক: জনাব মোঃ আনোয়ার হোসেন",
            establishedYear = "১৯৯০",
            villageOrLocation = "ঝালোরচর, ৬নং ওয়ার্ড",
            phoneNumber = "01736987654",
            totalStudentsOrInfo = "সরকারি প্রাথমিক বিদ্যালয়"
        ),
        EducationalInstitute(
            name = "জিগাবাড়ী সরকারি প্রাথমিক বিদ্যালয়",
            type = "প্রাথমিক বিদ্যালয়",
            headName = "প্রধান শিক্ষক: জনাব মোঃ শামীম আহমেদ",
            establishedYear = "১৯৯২",
            villageOrLocation = "জিগাবাড়ী, ৭নং ওয়ার্ড",
            phoneNumber = "01738876543",
            totalStudentsOrInfo = "সরকারি প্রাথমিক বিদ্যালয়"
        ),
        EducationalInstitute(
            name = "মেকারচর সরকারি প্রাথমিক বিদ্যালয়",
            type = "প্রাথমিক বিদ্যালয়",
            headName = "প্রধান শিক্ষক: জনাব মোঃ সাইফুল ইসলাম",
            establishedYear = "১৯৯১",
            villageOrLocation = "মেকারচর, ৮নং ওয়ার্ড",
            phoneNumber = "01740765432",
            totalStudentsOrInfo = "চরবাসীর মানসম্মত প্রাথমিক বিদ্যাপীঠ"
        ),
        EducationalInstitute(
            name = "মৌলভীর চর সরকারি প্রাথমিক বিদ্যালয়",
            type = "প্রাথমিক বিদ্যালয়",
            headName = "প্রধান শিক্ষক: জনাব মোঃ রফিকুল আলম",
            establishedYear = "১৯৮৬",
            villageOrLocation = "মৌলভীর চর, ৯নং ওয়ার্ড",
            phoneNumber = "01742654321",
            totalStudentsOrInfo = "নদী তীরবর্তী চরাঞ্চলের স্কুল"
        )
    )

    val villages = listOf(
        VillageInfo(
            villageName = "সানন্দবাড়ী (বাজার ও পূর্বপাড়া)",
            wardNumber = "১নং ওয়ার্ড",
            householdCount = "৮৫০ টি পরিবার",
            approximatePopulation = "৪,৫০০ জন",
            landmarksOrDescription = "ঐতিহাসিক সানন্দবাড়ী হাট, কলেজ, উচ্চ বিদ্যালয়, পুলিশ তদন্ত কেন্দ্র ও জিনজিরাম নদী ঘাট"
        ),
        VillageInfo(
            villageName = "চর আমখাওয়া (মূল গ্রাম)",
            wardNumber = "২নং ওয়ার্ড",
            householdCount = "৭২০ টি পরিবার",
            approximatePopulation = "৩,৮০০ জন",
            landmarksOrDescription = "চর আমখাওয়া পরিষদ ভবন, স্বাস্থ্য কমপ্লেক্স, প্রাচীন প্রাথমিক বিদ্যালয়"
        ),
        VillageInfo(
            villageName = "পাথরের চর",
            wardNumber = "৩নং ওয়ার্ড",
            householdCount = "৬৫০ টি পরিবার",
            approximatePopulation = "৩,৪০০ জন",
            landmarksOrDescription = "উর্বর ফসলি চরাঞ্চল, গম, ভুট্টা ও মরিচ চাষের জন্য বিখ্যাত"
        ),
        VillageInfo(
            villageName = "লম্বাপাড়া",
            wardNumber = "৪নং ওয়ার্ড",
            householdCount = "৫৮০ টি পরিবার",
            approximatePopulation = "৩,১০০ জন",
            landmarksOrDescription = "লম্বাপাড়া জামে মসজিদ ও সরকারি প্রাথমিক বিদ্যালয় এলাকা"
        ),
        VillageInfo(
            villageName = "বয়ড়াপাড়া ও ডিগ্রিরচর",
            wardNumber = "৫নং ওয়ার্ড",
            householdCount = "৬৯০ টি পরিবার",
            approximatePopulation = "৩,৬০০ জন",
            landmarksOrDescription = "কৃষিভিত্তিক জনবসতি ও পশু পালন খামার অঞ্চল"
        ),
        VillageInfo(
            villageName = "ঝালোরচর ও সরকারপাড়া",
            wardNumber = "৬নং ওয়ার্ড",
            householdCount = "৫২০ টি পরিবার",
            approximatePopulation = "২,৮০০ জন",
            landmarksOrDescription = "নদী তীরবর্তী শান্ত মনোরম পরিবেশ, মৎস্য শিকার ও কৃষিকাজ"
        ),
        VillageInfo(
            villageName = "জিগাবাড়ী ও মধ্যপাড়া",
            wardNumber = "৭নং ওয়ার্ড",
            householdCount = "৬১০ টি পরিবার",
            approximatePopulation = "৩,২০০ জন",
            landmarksOrDescription = "জিগাবাড়ী সরকারি প্রাথমিক বিদ্যালয় ও বাজার সংযোগ সড়ক"
        ),
        VillageInfo(
            villageName = "মেকারচর ও আনন্দপাড়া",
            wardNumber = "৮নং ওয়ার্ড",
            householdCount = "৫০০ টি পরিবার",
            approximatePopulation = "২,৬৫০ জন",
            landmarksOrDescription = "চারিদিকে সবুজ চারণভূমি, ভুট্টা ও বাদাম আবাদের জন্য সুপরিচিত"
        ),
        VillageInfo(
            villageName = "মৌলভীর চর ও ফুটানী বাজার এলাকা",
            wardNumber = "৯নং ওয়ার্ড",
            householdCount = "৬৪০ টি পরিবার",
            approximatePopulation = "৩,৩০০ জন",
            landmarksOrDescription = "ফুটানী বাজার ও নদী ঘাটের ব্যবসায়িক কেন্দ্র"
        )
    )

    val parishadOfficials = listOf(
        ParishadOfficial(
            name = "জনাব মোঃ আব্দুল আজিজ আকন্দ",
            roleType = "চেয়ারম্যান",
            designation = "চেয়ারম্যান, চর আমখাওয়া ইউনিয়ন পরিষদ",
            wardOrArea = "সমগ্র ইউনিয়ন",
            villageAddress = "সানন্দবাড়ী",
            phoneNumber = "01712345678",
            dutyTime = "সকাল ৯টা - বিকাল ৫টা (জনসাধারণের সেবা)"
        ),
        ParishadOfficial(
            name = "জনাব মোঃ শফিকুল ইসলাম",
            roleType = "সচিব",
            designation = "ইউনিয়ন পরিষদ সচিব",
            wardOrArea = "পরিষদ কার্যালয়",
            villageAddress = "চর আমখাওয়া পরিষদ ভবন",
            phoneNumber = "01713456789",
            dutyTime = "সকাল ৯টা - বিকাল ৫টা (নাগরিক ও জন্ম নিবন্ধন সনদ)"
        ),
        ParishadOfficial(
            name = "মোঃ রফিকুল ইসলাম",
            roleType = "ইউপি সদস্য",
            designation = "মেম্বার, ১নং ওয়ার্ড",
            wardOrArea = "১নং ওয়ার্ড",
            villageAddress = "গ্রাম: সানন্দবাড়ী",
            phoneNumber = "01714567890"
        ),
        ParishadOfficial(
            name = "মোঃ জহুরুল হক",
            roleType = "ইউপি সদস্য",
            designation = "মেম্বার, ২নং ওয়ার্ড",
            wardOrArea = "২নং ওয়ার্ড",
            villageAddress = "গ্রাম: চর আমখাওয়া",
            phoneNumber = "01715678901"
        ),
        ParishadOfficial(
            name = "মোঃ মোস্তফা কামাল",
            roleType = "ইউপি সদস্য",
            designation = "মেম্বার, ৩নং ওয়ার্ড",
            wardOrArea = "৩নং ওয়ার্ড",
            villageAddress = "গ্রাম: পাথরের চর",
            phoneNumber = "01716789012"
        ),
        ParishadOfficial(
            name = "মোঃ আনোয়ার হোসেন",
            roleType = "ইউপি সদস্য",
            designation = "মেম্বার, ৪নং ওয়ার্ড",
            wardOrArea = "৪নং ওয়ার্ড",
            villageAddress = "গ্রাম: লম্বাপাড়া",
            phoneNumber = "01717890123"
        ),
        ParishadOfficial(
            name = "মোঃ খলিলুর রহমান",
            roleType = "ইউপি সদস্য",
            designation = "মেম্বার, ৫নং ওয়ার্ড",
            wardOrArea = "৫নং ওয়ার্ড",
            villageAddress = "গ্রাম: বয়ড়াপাড়া",
            phoneNumber = "01718901234"
        ),
        ParishadOfficial(
            name = "মোঃ ফজলুল হক",
            roleType = "ইউপি সদস্য",
            designation = "মেম্বার, ৬নং ওয়ার্ড",
            wardOrArea = "৬নং ওয়ার্ড",
            villageAddress = "গ্রাম: ঝালোরচর",
            phoneNumber = "01719012345"
        ),
        ParishadOfficial(
            name = "মোঃ শহিদুল ইসলাম",
            roleType = "ইউপি সদস্য",
            designation = "মেম্বার, ৭নং ওয়ার্ড",
            wardOrArea = "৭নং ওয়ার্ড",
            villageAddress = "গ্রাম: জিগাবাড়ী",
            phoneNumber = "01720123456"
        ),
        ParishadOfficial(
            name = "মোঃ আবু বক্কর সিদ্দিক",
            roleType = "ইউপি সদস্য",
            designation = "মেম্বার, ৮নং ওয়ার্ড",
            wardOrArea = "৮নং ওয়ার্ড",
            villageAddress = "গ্রাম: মেকারচর",
            phoneNumber = "01721234567"
        ),
        ParishadOfficial(
            name = "মোঃ নুরুল ইসলাম",
            roleType = "ইউপি সদস্য",
            designation = "মেম্বার, ৯নং ওয়ার্ড",
            wardOrArea = "৯নং ওয়ার্ড",
            villageAddress = "গ্রাম: মৌলভীর চর",
            phoneNumber = "01722345678"
        ),
        ParishadOfficial(
            name = "মোছাঃ ফরিদা বেগম",
            roleType = "সংরক্ষিত মহিলা সদস্য",
            designation = "মহিলা মেম্বার (১, ২ ও ৩ নং ওয়ার্ড)",
            wardOrArea = "১, ২, ৩ নং ওয়ার্ড",
            villageAddress = "গ্রাম: সানন্দবাড়ী",
            phoneNumber = "01723456789"
        ),
        ParishadOfficial(
            name = "মোছাঃ মাজেদা খাতুন",
            roleType = "সংরক্ষিত মহিলা সদস্য",
            designation = "মহিলা মেম্বার (৪, ৫ ও ৬ নং ওয়ার্ড)",
            wardOrArea = "৪, ৫, ৬ নং ওয়ার্ড",
            villageAddress = "গ্রাম: লম্বাপাড়া",
            phoneNumber = "01724567890"
        ),
        ParishadOfficial(
            name = "মোছাঃ আছমা আক্তার",
            roleType = "সংরক্ষিত মহিলা সদস্য",
            designation = "মহিলা মেম্বার (৭, ৮ ও ৯ নং ওয়ার্ড)",
            wardOrArea = "৭, ৮, ৯ নং ওয়ার্ড",
            villageAddress = "গ্রাম: জিগাবাড়ী",
            phoneNumber = "01725678901"
        ),
        ParishadOfficial(
            name = "মোঃ মেহেদী হাসান",
            roleType = "ডিজিটাল সেন্টার উদ্যোক্তা",
            designation = "উদ্যোক্তা, ইউনিয়ন ডিজিটাল সেন্টার (UDC)",
            wardOrArea = "ডিজিটাল সেন্টার কক্ষ",
            villageAddress = "ইউনিয়ন পরিষদ ভবন, সানন্দবাড়ী",
            phoneNumber = "01726789012",
            dutyTime = "অনলাইন জন্ম নিবন্ধন, মৃত্যু নিবন্ধন, ওয়ারিশ ও নাগরিক সনদ"
        ),
        ParishadOfficial(
            name = "মোঃ আব্দুল কুদ্দুস",
            roleType = "গ্রাম পুলিশ/দফাদার",
            designation = "দফাদার (গ্রাম পুলিশ প্রধান)",
            wardOrArea = "সমগ্র ইউনিয়ন",
            villageAddress = "গ্রাম: চর আমখাওয়া",
            phoneNumber = "01727890123",
            dutyTime = "আইনশৃঙ্খলা রক্ষা ও সরকারি সমন জারি"
        ),
        ParishadOfficial(
            name = "মোঃ সামছুল হক",
            roleType = "গ্রাম পুলিশ/মহল্লাদার",
            designation = "মহল্লাদার (১-৩ নং ওয়ার্ড)",
            wardOrArea = "১, ২, ৩ নং ওয়ার্ড",
            villageAddress = "গ্রাম: সানন্দবাড়ী",
            phoneNumber = "01728901234"
        ),
        ParishadOfficial(
            name = "মোছাঃ শাহানাজ পারভীন",
            roleType = "স্বাস্থ্য ও পরিবার কল্যাণ সহকারী",
            designation = "স্বাস্থ্য সহকারী (কমিউনিটি ক্লিনিক)",
            wardOrArea = "স্বাস্থ্য কমপ্লেক্স ও ক্লিনিক",
            villageAddress = "সানন্দবাড়ী",
            phoneNumber = "01729012345",
            dutyTime = "বিনামূল্যে ওষুধ বিতরণ ও নিয়মিত ইপিআই টিকাদান"
        )
    )

    val doctors = listOf(
        DoctorInfo(
            name = "ডাঃ মোঃ মোস্তাফিজুর রহমান",
            specialty = "মেডিসিন ও বক্ষব্যাধি বিশেষজ্ঞ",
            degrees = "এমবিবিএস, বিসিএস (স্বাস্থ্য), এফসিপিএস (মেডিসিন)",
            chamberOrHospital = "সানন্দবাড়ী সেন্ট্রাল ডায়াগনস্টিক অ্যান্ড হাসপাতাল",
            visitingHours = "প্রতিদিন বিকাল ৪টা - রাত ৮:৩০টা",
            phoneNumber = "01720112233",
            consultationFee = "৩৫০ টাকা",
            location = "কলেজ রোড, সানন্দবাড়ী"
        ),
        DoctorInfo(
            name = "ডাঃ রাবেয়া সুলতানা",
            specialty = "স্ত্রী, প্রসূতি ও গাইনি বিশেষজ্ঞ",
            degrees = "এমবিবিএস, ডিজিও, সিএমইউ (আল্ট্রাসাউন্ড)",
            chamberOrHospital = "সানন্দবাড়ী মা ও শিশু স্বাস্থ্য কেন্দ্র",
            visitingHours = "সকাল ১০টা - দুপুর ২টা",
            phoneNumber = "01720223344",
            consultationFee = "বিনামূল্যে (সরকারি)",
            location = "হাসপাতাল চত্বর, সানন্দবাড়ী"
        ),
        DoctorInfo(
            name = "ডাঃ মফিজুল ইসলাম",
            specialty = "শিশু রোগ ও পুষ্টি বিশেষজ্ঞ",
            degrees = "এমবিবিএস, ডিসিএইচ (শিশু রোগ)",
            chamberOrHospital = "জনসেবা ক্লিনিক ও ফার্মেসি",
            visitingHours = "প্রতিদিন বিকাল ৫টা - রাত ৯টা",
            phoneNumber = "01720334455",
            consultationFee = "৩০০ টাকা",
            location = "সানন্দবাড়ী বাজার প্রধান সড়ক"
        ),
        DoctorInfo(
            name = "ডাঃ আনোয়ার হোসেন",
            specialty = "পল্লী চিকিৎসক ও প্রাথমিক ট্রমা কেয়ার",
            degrees = "ডিএমএফ, এলএমএএফ (রেজিস্টার্ড)",
            chamberOrHospital = "আনোয়ার ফার্মেসি ও চিকিৎসালয়",
            visitingHours = "সকাল ৭টা - রাত ১০টা (জরুরি সার্বক্ষণিক)",
            phoneNumber = "01720445566",
            consultationFee = "৫০ টাকা / সুলভ",
            location = "চর আমখাওয়া মোড়"
        )
    )

    val bloodDonors = listOf(
        BloodDonor(
            name = "জাহিদুল ইসলাম রনি",
            bloodGroup = "O+",
            villageOrWard = "গ্রাম: সানন্দবাড়ী (১নং ওয়ার্ড)",
            phoneNumber = "01811122334",
            lastDonationDate = "৩ মাস পূর্বে",
            isAvailable = true
        ),
        BloodDonor(
            name = "তারেক মাহমুদ",
            bloodGroup = "A+",
            villageOrWard = "গ্রাম: চর আমখাওয়া (২নং ওয়ার্ড)",
            phoneNumber = "01812233445",
            lastDonationDate = "৪ মাস পূর্বে",
            isAvailable = true
        ),
        BloodDonor(
            name = "শাকিল আহমেদ",
            bloodGroup = "B+",
            villageOrWard = "গ্রাম: পাথরের চর (৩নং ওয়ার্ড)",
            phoneNumber = "01813344556",
            lastDonationDate = "২ মাস পূর্বে",
            isAvailable = true
        ),
        BloodDonor(
            name = "মিজানুর রহমান",
            bloodGroup = "AB+",
            villageOrWard = "গ্রাম: লম্বাপাড়া (৪নং ওয়ার্ড)",
            phoneNumber = "01814455667",
            lastDonationDate = "৫ মাস পূর্বে",
            isAvailable = true
        ),
        BloodDonor(
            name = "সুজন মিয়া",
            bloodGroup = "O-",
            villageOrWard = "গ্রাম: বয়ড়াপাড়া (৫নং ওয়ার্ড)",
            phoneNumber = "01815566778",
            lastDonationDate = "৬ মাস পূর্বে",
            isAvailable = true
        ),
        BloodDonor(
            name = "মাহমুদুল হাসান",
            bloodGroup = "A-",
            villageOrWard = "গ্রাম: ঝালোরচর (৬নং ওয়ার্ড)",
            phoneNumber = "01816677889",
            lastDonationDate = "প্রযোজ্য নয়",
            isAvailable = true
        ),
        BloodDonor(
            name = "রুবেল হোসেন",
            bloodGroup = "B-",
            villageOrWard = "গ্রাম: জিগাবাড়ী (৭নং ওয়ার্ড)",
            phoneNumber = "01817788990",
            lastDonationDate = "৪ মাস পূর্বে",
            isAvailable = true
        ),
        BloodDonor(
            name = "আল-আমিন",
            bloodGroup = "AB-",
            villageOrWard = "গ্রাম: মৌলভীর চর (৯নং ওয়ার্ড)",
            phoneNumber = "01818899001",
            lastDonationDate = "৩ মাস পূর্বে",
            isAvailable = true
        )
    )

    val marketMembers = listOf(
        MarketMember(
            name = "হাজী মোঃ মোশাররফ হোসেন",
            designation = "সভাপতি",
            marketName = "ঐতিহাসিক সানন্দবাড়ী বাজার বণিক সমিতি",
            phoneNumber = "01730112233",
            shopName = "মোশাররফ ট্রেডার্স ও সার ডিলার"
        ),
        MarketMember(
            name = "মোঃ হারুনুর রশিদ",
            designation = "সাধারণ সম্পাদক",
            marketName = "ঐতিহাসিক সানন্দবাড়ী বাজার বণিক সমিতি",
            phoneNumber = "01730223344",
            shopName = "রশিদ বস্ত্রালয়"
        ),
        MarketMember(
            name = "আলহাজ্ব আব্দুল বারী",
            designation = "সহ-সভাপতি",
            marketName = "ঐতিহাসিক সানন্দবাড়ী বাজার বণিক সমিতি",
            phoneNumber = "01730334455",
            shopName = "বারী ইলেকট্রনিক্স ও হার্ডওয়্যার"
        ),
        MarketMember(
            name = "মোঃ মজিবুর রহমান",
            designation = "কোষাধ্যক্ষ",
            marketName = "ঐতিহাসিক সানন্দবাড়ী বাজার বণিক সমিতি",
            phoneNumber = "01730445566",
            shopName = "মজিবুর স্টোর"
        ),
        MarketMember(
            name = "মোঃ রুকনুজ্জামান",
            designation = "নিরাপত্তা ও শৃঙ্খলা ইনচার্জ",
            marketName = "ঐতিহাসিক সানন্দবাড়ী বাজার বণিক সমিতি",
            phoneNumber = "01730556677",
            shopName = "সানন্দবাড়ী মোবাইল গ্যালারি"
        )
    )

    val shops = listOf(
        ShopInfo(
            shopName = "সানন্দবাড়ী মডেল ফার্মেসি",
            ownerName = "মোঃ রফিকুল ইসলাম",
            category = "ফার্মেসি",
            marketName = "সানন্দবাড়ী বাজার",
            shopNumberOrLane = "দোকান নং ১, কলেজ রোড মোড়",
            phoneNumber = "01740112233",
            openingHours = "২৪ ঘণ্টা সার্বক্ষণিক খোলা"
        ),
        ShopInfo(
            shopName = "মেসার্স যমুনা ট্রেডার্স (ভুট্টা, ধান ও বাদাম আড়ৎ)",
            ownerName = "মোঃ আনিসুর রহমান",
            category = "শস্য ও পাইকারি আড়ৎ",
            marketName = "সানন্দবাড়ী বাজার",
            shopNumberOrLane = "নদী ঘাট রোড, শস্য গলি",
            phoneNumber = "01740223344",
            openingHours = "সকাল ৭টা - রাত ৯টা (শনিবার ও বুধবার হাট)"
        ),
        ShopInfo(
            shopName = "বিসমিল্লাহ ডিপার্টমেন্টাল স্টোর",
            ownerName = "হাজী মোবারক আলী",
            category = "মুদি ও জেনারেল",
            marketName = "সানন্দবাড়ী বাজার",
            shopNumberOrLane = "দোকান নং ১২, মধ্য গলি",
            phoneNumber = "01740334455",
            openingHours = "সকাল ৭টা - রাত ১১টা"
        ),
        ShopInfo(
            shopName = "সানন্দবাড়ী ডিজিটাল টেলিকম ও কম্পিউটার",
            ownerName = "মোঃ নাজমুল হুদা",
            category = "মোবাইল ও কম্পিউটার",
            marketName = "সানন্দবাড়ী বাজার",
            shopNumberOrLane = "সোনালী ব্যাংক সংলগ্ন, ২য় তলা",
            phoneNumber = "01740445566",
            openingHours = "সকাল ৮টা - রাত ১০টা"
        ),
        ShopInfo(
            shopName = "আল্লাহর দান কাঁচাবাজার আড়ৎ",
            ownerName = "শফিকুল ইসলাম",
            category = "কাঁচাবাজার",
            marketName = "সানন্দবাড়ী বাজার",
            shopNumberOrLane = "কাঁচাবাজার শেড",
            phoneNumber = "01740556677",
            openingHours = "ভোর ৫টা - রাত ৯টা"
        ),
        ShopInfo(
            shopName = "মিষ্টি মেলা ও ক্যাফে সানন্দবাড়ী",
            ownerName = "আব্দুল খালেক",
            category = "রেস্তোরাঁ ও মিষ্টি",
            marketName = "সানন্দবাড়ী বাজার",
            shopNumberOrLane = "বাস স্ট্যান্ড মোড়",
            phoneNumber = "01740667788",
            openingHours = "সকাল ৬টা - রাত ১১টা"
        )
    )

    val krishiAdvices = listOf(
        KrishiAdvice(
            title = "চরাঞ্চলে ভুট্টা ও চীনা বাদামের ফলন বৃদ্ধির পরিচর্যা",
            category = "ভুট্টা ও বাদাম",
            summary = "সানন্দবাড়ী ও চরাঞ্চলের বালুময় দোআঁশ মাটিতে সেচ ও সুষম সার প্রয়োগ।",
            details = "ভুট্টা গাছে ৮-১০ পাতা বের হলে ১ম সেচ ও ইউরিয়া সার উপরিপ্রয়োগ করুন। চরাঞ্চলের বালু মাটিতে বাদাম গাছের গোড়ায় মাটি তুলে দিন এবং জিপসাম সার প্রয়োগ নিশ্চিত করুন।",
            seasonOrMonth = "মৌসুম: রবি ও খরিপ",
            officerName = "জনাব মোঃ তরিকুল ইসলাম (উপসহকারী কৃষি কর্মকর্তা, সানন্দবাড়ী ব্লক)",
            officerPhone = "01750112233"
        ),
        KrishiAdvice(
            title = "বন্যা ও ঢলের পর চরাঞ্চলে মরিচ ও শাকসবজি রক্ষা পরামর্শ",
            category = "শাকসবজি",
            summary = "ঢলে পড়া রোগ ও ছত্রাক আক্রমণ প্রতিহত করার সঠিক ছত্রাকনাশক স্প্রে।",
            details = "পানি নেমে যাওয়ার সাথে সাথে ড্রেন পরিষ্কার করুন। প্রতি লিটার পানিতে রিডোমিল গোল্ড ২ গ্রাম মিশিয়ে গোঁড়া ভিজিয়ে স্প্রে করুন। অতিরিক্ত ইউরিয়া সার পরিহার করুন।",
            seasonOrMonth = "বর্ষা ও পরবর্তী মৌসুম",
            officerName = "জনাব মোঃ তরিকুল ইসলাম (উপসহকারী কৃষি কর্মকর্তা)",
            officerPhone = "01750112233"
        ),
        KrishiAdvice(
            title = "গবাদি পশুর বাদলা ও লাম্পি স্কিন ভ্যাকসিনেশন সতর্কতা",
            category = "পশুপালন",
            summary = "চরের খামারিদের জন্য নিয়মিত সরকারি টিকাদান ও প্রাথমিক চিকিৎসা।",
            details = "গোয়ালঘর সবসময় শুকনো ও পরিষ্কার রাখুন। পশুর শরীরে ক্ষত থাকলে পটাশ বা নিমপাতা সিদ্ধ পানি দিয়ে পরিষ্কার করুন। অবিলম্বে উপজেলা প্রাণিসম্পদ কর্মকর্তার ভ্যাকসিনের আওতায় আনুন।",
            seasonOrMonth = "সারা বছর",
            officerName = "ডাঃ রফিকুল ইসলাম (ভেটেরিনারি সার্জন, দেওয়ানগঞ্জ)",
            officerPhone = "01750223344"
        )
    )

    val notices = listOf(
        NoticeItem(
            title = "জরুরি ঘোষণা: সানন্দবাড়ী কেন্দ্রে বিনামূল্যে ভিটামিন 'এ' ও টাইফয়েড টিকা",
            description = "আগামীকাল সকাল ৯টা থেকে চর আমখাওয়া ইউনিয়ন পরিষদ ও সানন্দবাড়ী স্বাস্থ্য কেন্দ্রে ৬ থেকে ৫৯ মাস বয়সী সকল শিশুকে বিনামূল্যে ভিটামিন 'এ' ক্যাপসুল ও নিয়মিত টিকা খাওয়ানো হবে।",
            category = "স্বাস্থ্য ক্যাম্প",
            date = "আজ",
            isUrgent = true,
            publisher = "চর আমখাওয়া ইউনিয়ন স্বাস্থ্য ও পরিবার কল্যাণ কেন্দ্র"
        ),
        NoticeItem(
            title = "শনিবার ঐতিহ্যবাহী সানন্দবাড়ী সাপ্তাহিক হাটের শৃঙ্খলারক্ষা নির্দেশনা",
            description = "ঐতিহাসিক সানন্দবাড়ী শনিবারের হাটে নদী ঘাট ও প্রধান সড়কে যানবাহন পার্কিং নিষিদ্ধ করা হয়েছে। ক্রেতা-বিক্রেতাদের নির্ধারিত শেডে কেনাবেচা করার জন্য অনুরোধ করা হলো।",
            category = "বাজার নোটিশ",
            date = "গতকাল",
            isUrgent = true,
            publisher = "সানন্দবাড়ী পুলিশ তদন্ত কেন্দ্র ও বণিক সমিতি"
        ),
        NoticeItem(
            title = "ভিজিএফ ও খাদ্যবান্ধব কর্মসূচির ১০ টাকা কেজি চাল বিতরণ",
            description = "চর আমখাওয়া ইউনিয়ন পরিষদ প্রাঙ্গণে ১ থেকে ৯ নং ওয়ার্ডের তালিকাভুক্ত সুবিধাভোগীদের মাঝে ভিজিএফ চাল বিতরণ করা হবে। নিজ নিজ কার্ড ও এনআইডি সঙ্গে রাখুন।",
            category = "সরকারি অনুদান",
            date = "গত পরশু",
            isUrgent = false,
            publisher = "চর আমখাওয়া ইউনিয়ন পরিষদ কার্যালয়"
        )
    )

    val sampleSubmissions = listOf(
        UserSubmission(
            submissionType = "EDUCATION",
            titleOrName = "সানন্দবাড়ী নুরানী ও হাফেজিয়া মাদ্রাসা",
            secondaryInfo = "মুহতামিম: মাওলানা হাফেজ আহমদ",
            category = "মাদ্রাসা",
            phoneNumber = "01777112233",
            extraDetails = "সানন্দবাড়ী পূর্বপাড়া। কুরআন হিফজ ও প্রাথমিক ইসলামিক শিক্ষা।",
            submittedBy = "এলাকাবাসী",
            submittedDate = "আজ সকাল ৯:৩০",
            status = "PENDING"
        ),
        UserSubmission(
            submissionType = "BLOOD_DONOR",
            titleOrName = "মোঃ সোহেল রানা",
            secondaryInfo = "রক্তের গ্রুপ: O+",
            category = "রক্তদাতা",
            phoneNumber = "01999887766",
            extraDetails = "গ্রাম: সানন্দবাড়ী বাজার পাড়া। নিয়মিত রক্ত দিতে আগ্রহী।",
            submittedBy = "সোহেল রানা",
            submittedDate = "আজ দুপুর ১২:১৫",
            status = "PENDING"
        )
    )
}
