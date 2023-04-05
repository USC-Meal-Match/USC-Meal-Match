"""
coded by 04/03/2023
Changzhong Qian
"""

import requests
from bs4 import BeautifulSoup
from datetime import datetime

"""
@param session: 
    1 = breakfast menu
    2 = lunch menu
    3 = dinner menu
@param location:
    1 = parkside
    2 = village
    3 = evk
@return: a list with first element as the dish name, second element is a list of allergy
"""
def crawl_menus(session, location):
    if session == 1:
        meal = "breakfast"
    elif session == 2:
        meal = "lunch"
    elif session == 3:
        meal = "brunch"
    elif session == 4:
        meal = "dinner"
    if location == 1:
        loc = "?menu_venue=venue-518&menu_date="
    elif location == 2:
        loc = "?menu_venue=venue-27229&menu_date="
    elif location == 3:
        loc = "?menu_venue=venue-514&menu_date="
    current_date = datetime.now()
    formatted_date = current_date.strftime("%B+%d%%2C+%Y")
    url = "https://hospitality.usc.edu/residential-dining-menus/" + loc + formatted_date
    response = requests.get(url)
    if response.status_code != 200:
        print(f"Failed to fetch page: {response.status_code}")
        return None

    soup = BeautifulSoup(response.text, "html.parser")

    # Find the menu sections
    menu_sections = soup.find_all("div", class_="col-sm-6 col-md-4")
    menus = []

    for menu in menu_sections:
        title = menu.find('h3', class_='menu-venue-title')
        if title.text.strip().lower() == meal:
            select_menu = menu.find_all('ul', class_='menu-item-list')
            break

    for ul in select_menu:
        for item in ul.find_all("li"):
            menu_name = item.contents[0]
            menu_allergen = []
            allergens = item.find_all('span', class_='fa-allergen-container')
            for allergen in allergens:
                typeAllergen = allergen.find('span', class_=None)
                if typeAllergen is not None:
                    menu_allergen.append(typeAllergen.text)

            menus.append({
                "name": menu_name,
                "allergy": menu_allergen
            })



    return menus


def main():
    menus = crawl_menus(1,1)
    if menus:
        for menu in menus:
            print(f"{menu['name']}:")

            for item in menu['allergy']:
                print(f"  - {item}")

            print()


if __name__ == "__main__":
    main()
