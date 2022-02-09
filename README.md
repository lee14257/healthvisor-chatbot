# Healthvisor Chatbot
> Group Project for COMP3111H - Software Engineering
> Created using the Java Enterprise Edition (J2EE) template framework provided by the LINE forked from  https://github.com/line/line-bot-sdk-java.

Healthvisor is a chatbot that runs on the instant message software LINE. It assists users to obtain nutritional details of selected food menus, and provides healthy dietary recommendations when deciding what to eat. 

## Features
- Creates a profile for the user that includes interests in food, weight (with timestamp), past meals, etc.
- Reads menus in a variety of data format (e.g. text files, JSON, JPEG pictures)
- Provides nutritional information about the requested food menu (e.g. calories, saturated fat, carbohydrate, sodium, etc.)
- Creates a report of the user's daily dietary progress
- Assists the user to pick a food from a list of menu utilizing a unique recommendation algorithm

## Recommendation Algorithm
<img src="/docs/img/algorithm.png" width="400" height="400">

## JPEG Image Recognition
<img src="/docs/img/model.png" width="710" height="225">

## Data Infrastructure
![alt text](/docs/img/data_arch.png)
