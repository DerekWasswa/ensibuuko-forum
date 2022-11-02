package com.ensibuuko.android_dev_coding_assigment

import com.ensibuuko.android_dev_coding_assigment.data.models.*

val address = Address(
    "Kulas Light",
    "Apt. 556",
    "Gwenborough",
    "33"
)

val company = Company("Romano", "Feels better", "Better")

val dummyUser = User(
    100,
    "Leanne Graham",
    "Bret",
    "sincere@april.biz",
    address,
    "900000000",
    "bret.com",
    company
)

val dummyPost = Post(
    101,
    100,
    "qui rerum deleniti ut occaecati",
    "est natus enim nihil est dolore omnis voluptatem numquam",
    false
)

val dummyLocalPost = Post(
    102,
    100,
    "qui rerum deleniti ut occaecati",
    "est natus enim nihil est dolore omnis voluptatem numquam",
    false
)

val dummyComment = Comment(
    3,
    101,
    "id labore ex et quam laborum",
    "sincere@april.biz",
    "reiciendis et nam sapiente accusantium",
    false
)

val dummyLocalComment = Comment(
    4,
    101,
    "id labore ex et quam laborum",
    "sincere@april.biz",
    "reiciendis et nam sapiente accusantium",
    false
)