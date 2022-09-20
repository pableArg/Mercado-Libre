
# ChallengSkillLevelling

ChallengeSkillLevelling is an application that allows you to search for the 20 most searched products according to your search criteria. It also allows you to see the details of each product and be able to save it as a favorite.


## API Reference

#### Get Category

Retrieves a category according to the query you send by parameter
@parm category : the name of category
```http
  GET "sites/$SITE_ID/domain_discovery/search"
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `q` | `string` | **Required**. Your API key |
| `limit` | `string` | **Required**. Your API key |

#### Get highlights
Retrieve a list of highlights
@parameters categoryId : Is the category_id retrieved from getCategory
```http
  GET "highlights/MLA/category/{categoryId}"
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `categoryId`      | `string` | **Required**. Id of item to fetch |


#### Get Items
You have to create a string separated by commas, these strings are retrieved from the id of highlight
@parameters itemList : the retrieved string
```http
  GET "items"
```


| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `idList`      | `string` | **Required**. Id of item to fetch |



## Authors

- [pableArg](https://www.github.com/pableArg)


## Usage

### Main Activity
##### It has a searView in which entering any product you want to buy,A recyclerView will be displayed with the 20 most searched products on the free market page. Then you can click on any object and another Activity will be displayed.You can also click on the cart, which will display the favorite activity


### Detail Activity
##### After clicking, this Activity will be displayed, where it will show details of the selected product such as, photo, price, title, subtitle, description. You will also have the option to add said product to favorites


### favorite Activity
##### This activity will show details of the last product added to favorites


## Features

###Main Activity
#### Allows you to enter a query for , which will be sent to getCategories of the ItemViewModel
 private fun setSearchViewListener()
#### Assign the adapter, send the Activity context and a list of items to be rendered, also add a decoration item to separate the products
  private fun initRecyclerView()

#### through the ItemViewModel, the oberserver pattern will be used, to see changes in the list, if it is null or empty it will show a snack bar notifying the user, in the case that it contains information, the adapter will be notified of the changes that have arisen on the list
private fun setupObservers()

#### Bind the image and with one click navigate to the favorite Activity
 private fun onClickFav()

#### Show the user an error message when loading the products
 private fun snackBar()


