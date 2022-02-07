class Food:
    name = ''
    category = ''
    calories = ''
    sodium = ''
    fat = ''
    protein = ''
    carbohydrate = ''

foods = {}
foodGroups = {}
nutrientIds = {
    '203': 'protein',
    '204': 'fat',
    '205': 'carbohydrate',
    '208': 'calories',
    '307': 'sodium',
}

with open('FD_GROUP.txt', 'r') as foodGroupFile:
    for line in foodGroupFile:
        data = line.split('^')
        groupId = data[0].replace('~', '')
        groupName = data[1].replace('~', '').strip()
        foodGroups[groupId] = groupName

with open('FOOD_DES.txt', 'r') as foodNameFile:
    for line in foodNameFile:
        data = line.split('^')
        food = Food()
        foodId = data[0].replace('~', '')
        food.category = foodGroups[data[1].replace('~', '')]
        food.name = data[2].replace('~', '')
        foods[foodId] = food

with open('NUT_DATA.txt', 'r') as nutritionFile:
    for line in nutritionFile:
        data = line.split('^')
        foodId = data[0].replace('~', '')
        nutrientId = data[1].replace('~', '')
        if nutrientId in nutrientIds:
            nutrient = nutrientIds[nutrientId]
            food = foods[foodId]
            setattr(food, nutrient, data[2])

with open('FOOD_DATA.txt', 'w') as foodDataFile:
    for foodId in foods:
        food = foods[foodId]
        foodDataFile.write(food.name)
        foodDataFile.write('^')
        foodDataFile.write(food.category)
        foodDataFile.write('^')
        foodDataFile.write(food.calories)
        foodDataFile.write('^')
        foodDataFile.write(food.sodium)
        foodDataFile.write('^')
        foodDataFile.write(food.fat)
        foodDataFile.write('^')
        foodDataFile.write(food.protein)
        foodDataFile.write('^')
        foodDataFile.write(food.carbohydrate)
        foodDataFile.write('\n')
