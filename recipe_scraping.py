from urllib import urlopen
import html5lib
import unicodedata
import json

#send http request
webpage = urlopen('https://www.allrecipes.com/recipes/86/world-cuisine/').read()

from bs4 import BeautifulSoup
soup = BeautifulSoup(webpage, "html5lib")

# get ul classes
all_ul = soup.findAll('ul', {'class': 'browse-hubs__subcategories'})

links = []
for ul in all_ul:
    for li in ul.findAll('li'):
        link = li.a.get('href')
        str_link = unicodedata.normalize('NFKD', link).encode('ascii', 'ignore')
        if 'world-cuisine' in str_link:
            links.append(str_link)
            
cuisines = []
i = 0
for link in links:
    temp_webpage = urlopen(link).read()
    soup_cuisine = BeautifulSoup(temp_webpage, "html5lib")
    all_articles = soup_cuisine.findAll('article', {'class': 'fixed-recipe-card'})
    recipes = []
    for article in all_articles:
        recipe_unicode = article.a.get('href')
        str_recipe_link = unicodedata.normalize('NFKD', recipe_unicode).encode('ascii', 'ignore')
        recipes.append(str_recipe_link)
    cuisines.append(recipes)

i = 0
ingredients_all_cuisines = []
for cuisine in cuisines:
    ingredients_all_recipes = []
    for recipe in cuisine:
        recipe_webpage = urlopen(recipe).read()
        soup_recipe = BeautifulSoup(recipe_webpage, "html5lib")
        all_inputs = soup_recipe.findAll('input', {'class': 'checkbox-list-input'})
        title_unicode = soup_recipe.title.text
        title = unicodedata.normalize('NFKD', title_unicode).encode('ascii', 'ignore')
        ingredients = []
        for inp in all_inputs:
            ingredient_unicode = inp.get('value')
            ingredient = unicodedata.normalize('NFKD', ingredient_unicode).encode('ascii', 'ignore')
            ingredients.append(ingredient)
        info = {'title': title, 'ingredients': ingredients, 'instructions': recipe}
        ingredients_all_recipes.append(info)
    ingredients_all_cuisines.append(ingredients_all_recipes)
output = {'asian': ingredients_all_cuisines[0], 'indian': ingredients_all_cuisines[1], 'italian': ingredients_all_cuisines[2], 'mexican': ingredients_all_cuisines[3]}

#save output file as json, csv, txt files
with open('recipe_data.json', 'w') as fp:
    json.dump(output, fp)