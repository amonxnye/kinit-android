package org.kinecosystem.kinit.mocks

import org.kinecosystem.kinit.navigation.Navigator
import org.kinecosystem.kinit.viewmodel.earn.CategoriesViewModel
import org.mockito.Mockito

private const val SAMPLE_CATEGORIES= """
{"categories":
[{"available_tasks_count":5,"id":"3","supported_os":"all","title":"survey","ui_data":{"color":"#F59C1F","header_image_url":"https://cdn.kinitapp.com/brand_img/survey_nav.png","image_url":"https://cdn.kinitapp.com/brand_img/survey_illu.png"}},
{"available_tasks_count":2,"id":"0","supported_os":"all","title":"trivia","ui_data":{"color":"#8E65EF","header_image_url":"https://cdn.kinitapp.com/brand_img/trivia_nav.png","image_url":"https://cdn.kinitapp.com/brand_img/trivia_illu.png"}},
{"available_tasks_count":2,"id":"4","supported_os":"all","title":"images","ui_data":{"color":"#F7A7B7","header_image_url":"https://cdn.kinitapp.com/brand_img/images_nav.png","image_url":"https://cdn.kinitapp.com/brand_img/images_illu.png"}},
{"available_tasks_count":3,"id":"2","supported_os":"all","title":"video","ui_data":{"color":"#3460D9","header_image_url":"https://cdn.kinitapp.com/brand_img/video_nav.png","image_url":"https://cdn.kinitapp.com/brand_img/video_illu.png"}},
{"available_tasks_count":3,"id":"1","supported_os":"all","title":"this or that","ui_data":{"color":"#B5E0FF","header_image_url":"https://cdn.kinitapp.com/brand_img/this_or_that_nav.png","image_url":"https://cdn.kinitapp.com/brand_img/this_or_that_illu.png"}}],
"header_message":{"subtitle":"Here are today`s activities","title":"Hi there"},
"status":"ok"}
"""


private const val SAMPLE_TASKS= """
{"show_captcha":false,"tasks":{"0":[{"cat_id":"0","desc":"To Infinity and Beyond","excluded_country_codes":[],"id":"163","items":[{"id":"1","quiz_data":{"answer_id":"2","explanation":"The International Astronomy Union recognizes 88 constellations, among which are Ursa Major and Minor, Aquarius, Orion and Canis Major.","reward":4},"results":[{"id":"1","text":"5"},{"id":"2","text":"88"},{"id":"3","text":"12"},{"id":"4","text":"120"}],"text":"How many constellations are officially recognized?","type":"text"},{"id":"2","quiz_data":{"answer_id":"1","explanation":"There are 8 planets in our solar system. In order from the inner solar system (closest to the sun) and outward: Mercury, Venus, Earth, Mars, Jupiter, Saturn, Uranus, Neptune.","reward":4},"results":[{"id":"1","text":"8"},{"id":"2","text":"12"},{"id":"3","text":"10"},{"id":"4","text":"7"}],"text":"With Pluto not considered a planet, how many planets remain in the  solar system?","type":"text"},{"id":"3","quiz_data":{"answer_id":"3","explanation":"Polaris is the current north star, but as the earth shifts its position over time, in a few thousand years Vega will take over.","reward":4},"results":[{"id":"1","text":"Aurora"},{"id":"2","text":"Vega"},{"id":"3","text":"Polaris"},{"id":"4","text":"Arthemis"}],"text":"What is the name of the north star?","type":"text"},{"id":"4","quiz_data":{"answer_id":"4","explanation":"Saturn has impressive rings around it, but Jupiter, Uranus and Neptune also have rings around them.","reward":4},"results":[{"id":"1","text":"Earth"},{"id":"2","text":"Venus"},{"id":"3","text":"Mars"},{"id":"4","text":"Saturn"}],"text":"Which planet has rings?","type":"text"},{"id":"5","quiz_data":{"answer_id":"3","explanation":"Jupiter has 63 moons, more than any other planet. Our Planet Earth has one, Mars has 2 moons and Venus has no moons at all.","reward":4},"results":[{"id":"1","text":"Venus"},{"id":"2","text":"Mars"},{"id":"3","text":"Jupiter"},{"id":"4","text":"Earth"}],"text":"Which planet has the most moons?","type":"text"}],"memo":"1-kit-s270bf5041c69454b9ce9f","min_client_version_android":"1.2.9","min_client_version_ios":"1.1.5","min_to_complete":1.0,"position":0,"post_task_actions":[],"price":5,"provider":{"image_url":"https://cdn.kinitapp.com/brand_img/quiz_logo.png","name":"Kinit"},"start_date":0,"tags":["Education"],"task_expiration_date":null,"task_start_date":null,"title":"Astronomy","type":"quiz","updated_at":1540804242}],"1":[{"cat_id":"1","desc":"Help others make better decisions in a unique voting experience","excluded_country_codes":[],"id":"3","items":[{"id":"1","results":[{"id":"1","image_url":"https://cdn.kinitapp.com/tasks/139/cdcd9c41-79f0-4adb-bd30-41bd3a8084c1_a.jpg","text":""},{"id":"2","image_url":"https://cdn.kinitapp.com/tasks/139/cdcd9c41-79f0-4adb-bd30-41bd3a8084c1_b.jpg","text":""}],"text":"Better game? #tablesoccer or #pool","type":"dual_image"},{"id":"2","results":[{"id":"1","image_url":"https://cdn.kinitapp.com/tasks/139/2b63168a-c9cc-4ab7-ad3e-e0f9d3fe04a9_a.jpg","text":""},{"id":"2","image_url":"https://cdn.kinitapp.com/tasks/139/2b63168a-c9cc-4ab7-ad3e-e0f9d3fe04a9_b.jpg","text":""}],"text":"Go for dinner. #sushi or #pizza","type":"dual_image"},{"id":"3","results":[{"id":"1","image_url":"https://cdn.kinitapp.com/tasks/139/3750bba1-73fa-44d3-ac4b-981524f01742_a.jpg","text":""},{"id":"2","image_url":"https://cdn.kinitapp.com/tasks/139/3750bba1-73fa-44d3-ac4b-981524f01742_b.jpg","text":""}],"text":"Where to go? #dubai or #greece","type":"dual_image"},{"id":"4","results":[{"id":"1","image_url":"https://cdn.kinitapp.com/tasks/139/0a86481e-3ff7-4def-b05e-3cedbabf8f30_a.jpg","text":""},{"id":"2","image_url":"https://cdn.kinitapp.com/tasks/139/0a86481e-3ff7-4def-b05e-3cedbabf8f30_b.jpg","text":""}],"text":"#whatdoyouprefer? #bungeejumping or #freefall? #adrenalinkick","type":"dual_image"},{"id":"5","results":[{"id":"1","image_url":"https://cdn.kinitapp.com/tasks/139/0bf1c4b9-27a6-409c-a2b9-86046c111e8d_a.jpg","text":""},
{"id":"2","image_url":"https://cdn.kinitapp.com/tasks/139/0bf1c4b9-27a6-409c-a2b9-86046c111e8d_b.jpg","text":""}],"text":"Burger King or McDonald's? #wheretoeat","type":"dual_image"}],"memo":"1-kit-sc890ebdffc1a4d7e85eaf","min_client_version_android":"1.2.7","min_client_version_ios":"1.0.0","min_to_complete":0.25,"position":-1,"post_task_actions":[],"price":6,"provider":{"image_url":"https://cdn.kinitapp.com/brand_img/poll_logo_swelly.png","name":"Swelly"},"start_date":0,"tags":["Social"],"task_expiration_date":"2018-11-25T08:10:58.970460+00:00","task_start_date":"2018-11-01T08:10:58.970460+00:00","title":"Priority AB for Sarit","type":"questionnaire","updated_at":1541516793}],"2":[{"cat_id":"2","desc":"Interactive experience with popular brands","excluded_country_codes":[],"id":"5","items":[],"memo":"1-kit-s882c931c593e4e4c95d7b","min_client_version_android":"1.0.2","min_client_version_ios":"1.0.0","min_to_complete":0.5,"position":0,"post_task_actions":[],"price":10,"provider":{"image_url":"https://cdn.kinitapp.com/brand_img/pollLogoKinVideo.png","name":"TrueX"},"start_date":0,"tags":["Entertainment"],"task_expiration_date":null,"task_start_date":null,"title":"1 - To video or not to video","type":"truex","updated_at":1540188622}],"3":[{"cat_id":"3","desc":"Complete this Welcome activity to earn your first sum of Kin!","excluded_country_codes":[],"id":"201","items":[{"id":"1","results":[{"id":"1","text":"Got it! Cool!"},{"id":"2","text":"I'll give it a try..."}],"text":"Welcome! you can earn Kin by simply completing tasks every day (like this activity)!","type":"text"},{"id":"2","results":[{"id":"1","text":"0 tasks"},{"id":"2","text":"1 task"},{"id":"3","text":"5 tasks"},{"id":"4","text":"20 tasks"},{"id":"5","text":"100 tasks"}],"text":"How many tasks similar to this survey are you willing to complete in a day?","type":"text"},{"id":"3","results":[{"id":"1","text":"Awesome!"},{"id":"2","text":"That's a bummer..."}],"text":"On some of the days, you\u2019ll be able to complete just one task to earn Kin.","type":"text"},{"id":"4","results":[{"id":"1","text":"Sounds great!"},{"id":"2","text":"Looking forward to it..."}],"text":"As a heads up, the Kin you earn can be used to buy things like gift cards and subscriptions.","type":"text"},{"id":"5","results":[{"id":"1","text":"Food delivery credits"},{"id":"2","text":"Gift cards"},{"id":"3","text":"Taxi ride codes"},{"id":"4","text":"Movie / Sport / Concert ticket codes"},{"id":"5","text":"Music app subscription code"},{"id":"6","text":"Streaming TV subscription code"}],"text":"Which of the following would you most likely spend your Kin on?","type":"textmultiple"},{"id":"6","results":[{"id":"1","text":"I was looking for a new experience"},{"id":"2","text":"I wanted to earn Kin"},{"id":"3","text":"I heard about it and was curious"},{"id":"4","text":"To spend Kin on things like gift cards"}],"text":"What is the main reason you decided to visit the Kinit app?","type":"text"},{"id":"7","results":[{"id":"1","text":"\ud83d\ude42 Very likely"},{"id":"2","text":"\ud83d\ude0f Somewhat likely"},{"id":"3","text":"\ud83d\ude41 Not likely"}],"text":"How likely are you to tell a friend about this app?","type":"textemoji"},{"id":"8","results":[{"id":"1","text":"\ud83d\ude0d I loved it!"},{"id":"2","text":"\ud83d\ude42 It was actually not that bad..."},{"id":"3","text":"\ud83d\ude15 It was a bit too long..."},{"id":"4","text":"\ud83d\ude20 I'll never do it again!"}],"text":"One last question! What do you think of this survey?","type":"textemoji"}],"memo":"1-kit-s7405bab7970e44d8bf966","min_client_version_android":"1.0.2","min_client_version_ios":"1.0.0","min_to_complete":2.0,"position":0,"post_task_actions":[],"price":80,"provider":{"image_url":"https://cdn.kinitapp.com/brand_img/poll_logo_kin.png","name":"Kinit Team"},"start_date":0,"tags":["Finance"],"task_expiration_date":null,"task_start_date":null,"title":"Welcome to Kin!","type":"questionnaire","updated_at":1540806809}],
"4":[{"cat_id":"4","desc":"How accurate are you when it comes to clothes?","excluded_country_codes":[],
"id":"947","items":[{"id":"1","image_url":"https://cdn.kinitapp.com/tasks/46/dress_image004.jpg",
"results":[{"id":"1","image_url":"https://cdn.kinitapp.com/brand_img/edgecase/Sweatheart_neckline.png","text":"Sweatheart"},
{"id":"2","image_url":"https://cdn.kinitapp.com/brand_img/edgecase/Square_neckline.png","text":"Square"},
{"id":"3","image_url":"https://cdn.kinitapp.com/brand_img/edgecase/round_neckline.png","text":"Round"},
{"id":"4","image_url":"https://cdn.kinitapp.com/brand_img/edgecase/DeepPlunge_neckline.png","text":"Deep Plunge"},
{"id":"5","image_url":"https://cdn.kinitapp.com/brand_img/edgecase/v_neckline.png","text":"V"},
{"id":"6","image_url":"https://cdn.kinitapp.com/brand_img/edgecase/non.png","text":"Other"}],"text":"The dress\u2019 neckline is","type":"textimage"},
{"id":"2","image_url":"https://cdn.kinitapp.com/tasks/46/dress_image004.jpg","results":[{"id":"1","image_url":"https://cdn.kinitapp.com/brand_img/edgecase/sleeveless.png","text":"Sleeveless"},{"id":"2","image_url":"https://cdn.kinitapp.com/brand_img/edgecase/SpagettiStrap.png","text":"Spagetti Strap"},{"id":"3","image_url":"https://cdn.kinitapp.com/brand_img/edgecase/ShortSleeve.png","text":"Short Sleeve"},{"id":"4","image_url":"https://cdn.kinitapp.com/brand_img/edgecase/ThreeQuarterSleeve.png","text":"Three Quarter"},{"id":"5","image_url":"https://cdn.kinitapp.com/brand_img/edgecase/Longsleeve.png","text":"Long Sleeve"},{"id":"6","image_url":"https://cdn.kinitapp.com/brand_img/edgecase/non.png","text":"Other"}],"text":"The sleeve style is...","type":"textimage"},{"id":"3","image_url":"https://cdn.kinitapp.com/tasks/46/dress_image004.jpg","results":[{"id":"1","text":"Yellow"},{"id":"2","text":"Orange"},{"id":"3","text":"Rose Gold"},{"id":"4","text":"Pink"},{"id":"5","text":"Red"},{"id":"6","text":"Yellow"}],"text":"The dress main color is...","type":"text"},{"id":"4","image_url":"https://cdn.kinitapp.com/tasks/46/dress_image004.jpg","results":[{"id":"1","image_url":"https://cdn.kinitapp.com/brand_img/edgecase/non.png","text":"No Pattern"},{"id":"2","image_url":"https://cdn.kinitapp.com/brand_img/edgecase/stripes.png","text":"Stripes"},{"id":"3","image_url":"https://cdn.kinitapp.com/brand_img/edgecase/plaid.png","text":"Plaid"},
{"id":"4","image_url":"https://cdn.kinitapp.com/brand_img/edgecase/polka_dot.png","text":"PolkaDots"},
{"id":"5","image_url":"https://cdn.kinitapp.com/brand_img/edgecase/camouflage.png","text":"Camouflage"},
{"id":"6","image_url":"https://cdn.kinitapp.com/brand_img/edgecase/floral.png","text":"Floral"}],"text":"The dress has the following pattern...","type":"textimage"}],
"memo":"1-kit-sbf804b0afc6b441389269","min_client_version_android":"1.0.5","min_client_version_ios":"1.0.0","min_to_complete":0.5,"position":-1,"post_task_actions":[],
"price":10,"provider":{"image_url":"https://cdn.kinitapp.com/brand_img/poll_logo_kin.png","name":"Kinit Team"},"start_date":0,"tags":["Finance"],"task_expiration_date":"2018-11-30T08:10:58.970460+00:00",
"task_start_date":"2018-11-01T08:10:58.970460+00:00","title":"High priority task for sarit","type":"questionnaire","updated_at":1541517646}]},"tz":"2"}
"""

class MockCategoriesViewModel(navigator: Navigator):CategoriesViewModel(navigator){
    init {
        categoriesRepository.updateTestData(SAMPLE_CATEGORIES, SAMPLE_TASKS)
    }
}