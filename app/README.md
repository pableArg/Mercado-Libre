
# ChallengSkillLevelling

ChallengeSkillLevelling is an application that allows you to search for the 20 most searched products according to your search criteria. It also allows you to see the details of each product and be able to save it as a favorite.


## Acknowledgements

 - [Awesome Readme Templates](https://awesomeopensource.com/project/elangosundar/awesome-README-templates)
 - [Awesome README](https://github.com/matiassingers/awesome-readme)
 - [How to write a Good readme](https://bulldogjob.com/news/449-how-to-write-a-good-readme-for-your-github-project)


## API Reference

#### Get Category

```http
  GET "sites/$SITE_ID/domain_discovery/search"
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `q` | `string` | **Required**. Your API key |
| `limit` | `string` | **Required**. Your API key |

#### Get item

```http
  GET "highlights/MLA/category/{categoryId}"
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `categoryId`      | `string` | **Required**. Id of item to fetch |

```http
  GET "items"
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `idList`      | `string` | **Required**. Id of item to fetch |

