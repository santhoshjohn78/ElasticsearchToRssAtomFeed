# ElasticsearchToRssAtomFeed
This project exposes the contents of an Elasticsearch index as an Rss or Atom Feed.



## Getting Started

Clone or download this project from github and use your fav IDE to import as a maven project.

```
git clone <url>
mvn clean install
```

### Prerequisites

This project needs ElasticSearch 5.6 running somewhere. Update the property file with
the host and port. This also assumes that you have created some indices in ElasticSearch.

```
elasticsearch.host=localhost
elasticsearch.port=9200
```

### Installing

This is built on maven

```
mvn clean install
```

This is a spring boot app. To start the app just run the main class.

```
java -jar elasticsearch_to_rssatom_feed-0.0.1-SNAPSHOT.jar

http://localhost:8080/{indexname}/atom
http://localhost:8080/{indexname}/rss
```

Where indexname is the name of the index in ElasicSearch

## Built With

* [Maven](https://maven.apache.org/) - Dependency Management


## Authors

* **Santhosh John** - *Initial work* - [EHSS](https://github.com/santhoshjohn78)

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details

## Acknowledgments

* Hat tip to anyone whose code was used
* Inspiration
* etc