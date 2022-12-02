# StockEasily-API

This directory contains the backend Java project based on a [Spring Initializr (Spring Boot)](https://start.spring.io/) project template.

## Contributing

The following tips may help you to contribute to this project.

### Adding a controller

You should mark the controller with `@RestController` annotation.  
Add `@RequestMapping(path = "/api/v1/<entities>", consumes = "application/json", produces = "application/json")`
`consumes` and `procudes` types are set as default for all requests but can also be overridden. 

See full example at below section [Annotate controllers](#annotate-controllers).

### Adding a request

#### @<Get/Post/Patch/Put/Delete>Mapping

You should define the request method via `@GetMapping`, `@PostMapping`, `@PatchMapping`, `@PutMapping` **or** `@DeleteMapping` annotation.  
Minified example:

```java
@PostMapping
//...
public ResponseEntity<ExampleResponseDto> createExample() {
    return new ResponseEntity<>(exampleFactory.create(), HttpStatus.CREATED);
} 
```

#### @ResponseStatus

Annotate the **success** response code via `@ResponseStatus(...)`.  
This annotation is important for Swagger to only display correct response codes.  
Minified example:

```java
//...
@ResponseStatus(HttpStatus.CREATED) // just pass the success status
public ResponseEntity<ExampleResponseDto> createExample() {
    ExampleResponseDto result = exampleFactory.create();
    return new ResponseEntity<>(result, result != null ? HttpStatus.CREATED : HttpStatus.NOT_IMPLEMENTED);
} 
```

### Request and Response DTOs

#### DTO
The [DTO **D**ata-**T**ransfer-**O**bject pattern](https://www.baeldung.com/java-dto-pattern) is just a primitive pattern to store data that should be transferred.  
It is used to convert, merge or minify objects or to hide specific properties that should not be sent e.g. you should not return the password after creating a user.

#### Request DTOs

We use request DTOs to transfer and validate the parameters _via body_.

For instance, you want to create a new article.  
You know certain parameters such as `name`, `image`, `category`, ...
_but you don't know the article ID_, so you cannot and should not pass it.  
So, additionally to our `Article` (model) we need a `CreateArticleRequestDto` to only transfer data needed.

_Model_
```java
public class Article {
    private long id;
    
    private String name; 
    
    private Blob image;
    
    // ...
    
    // getters and setters
    // ...
}
```

_CreateArticleRequestDto_
```java
public class CreateArticleRequestDto {
    // id is unknown when requesting new resource
    
    private String name; 
    
    private MultipartFile image; // Must be converted
    
    // ...
    
    // getters and setters
    // ...
}
```

#### Response DTOs

We use response DTOs to return exactly the data needed in frontend.

For instance, you want to create a new article.  
You have certain info (from request) such as `name`, `properties`, ... **and `image`**;
but you don't want to return the image back to reduce unnecessary payload.  
So, before sending the request from the frontend,
after selecting the image to upload, save it locally and display on success.  
It is not necessary to return the image unless compressing or modifying in another way.

(see corresponding Model above at section [Request DTOs](#request-dtos))

_CreateArticleRequestDto_
```java
public class CreateArticleResponseDto {
    private long id; // this is now returned
    
    private String name;
    
    // image is now missing
    
    // ...
    
    // getters and setters
    // ...
}
```

### Parameter Validation

To validate body parameters, use the `@Valid` annotation for the parameter.  
Add another parameter `BindingResult bindingResult` which we will use to handle errors when calling the request.

Validating body parameter (minified example):

```java
//...
public class ExampleController {
    //...
    
    //...
    public ResponseEntity<ExampleResponseDto> createExample(
            @Valid @RequestBody CreateExampleRequestDto request,
            BindingResult bindingResult) {
        // You should always do the following three lines first
        if (bindingResult.hasErrors()) {
            throw new InvalidBodyException(bindingResult);
        }
        ExampleResponseDto result = exampleService.create(request); // returns null when there is a creation conflict
        return new ResponseEntity<>(result, result != null ? HttpStatus.CREATED : HttpStatus.CONFLICT);
    }
    
    //...
}
```

You should set the validation constraints (annotations) in the respective **request** DTO.  
Add `@Valid` for each non-primitive type i.e. object - you can add their annotations inside the corresponding classes.

The constraint annotations should always be defined with a message - it will be returned to the frontend.

Minified example:

```java
public class CreateArticleRequestDto {
    @Valid
    private CategoryRequestDto category;

//    @ApiModelProperty(notes = "Article name", example = "HDX42 Widescreen Monitor", required = true)
    @NotNull(message = "Name is mandatory")
    @Size(min = 1, max = 30, message = "Article name must be between 1 and 30 characters")
    private String name;
    
    //...
}
```

#### Cheat sheet

The following annotations can be used to define the constraints from `javax.validation.constraints` package.

**General**

- `@AssertFalse`
- `@AssertTrue`
- `@NotBlank`
- `@NotEmpty`
- `@NotNull`
- `@Null`
- `@Size`

**Integers**

- `@DecimalMax`
- `@DecimalMin`
- `@Digits`
- `@Max`
- `@Min`
- `@Negative`
- `@NegativeOrZero`
- `@Positive`
- `@PositiveOrZero`

**Strings**

- `@Email`
- `@Pattern`

**Dates**

- `@Future`
- `@FutureOrPresent`
- `@Past`
- `@PastOrPresent`

Get more information [from the docs](https://javaee.github.io/javaee-spec/javadocs/javax/validation/constraints/package-summary.html).

### Setup Swagger

When adding a new request, you should add [Swagger](https://springfox.github.io/springfox/docs/current/) annotations that are collected here.

#### Annotate controllers

Add a tag to display via `@ApiTags(tags = <tags>)` annotation.

Full example:

```java
@Api(tags = {"Articles"}) // Set correct heading
@RestController
@RequestMapping(path = "/api/v1/articles", consumes = "application/json", produces = "application/json")
public class ArticleController {
    // REST functions
}
```

#### Annotate parameters

To annotate request parameters, you have two options, either passed via body or via URL.

Use the `@ApiParam(name = "<name>")` annotation to setup parameters correctly.

Passing via body (minified example):

```java
//...
public ResponseEntity<ExampleResponseDto> createExample(
    @ApiParam(name = "request") @Valid @RequestBody CreateExampleRequestDto request,
    BindingResult bindingResult) {
    
    ExampleResponseDto result = exampleFactory.create();
    return new ResponseEntity<>(result, result != null ? HttpStatus.CREATED : HttpStatus.CONFLICT);
} 
```

Passing via URL:

```java
//...
@PostMapping(value = "/api/v1/examples/{id}")
public ResponseEntity<ExampleResponseDto> createExample(
    @ApiParam(name = "id") @Positive @RequestParam(name = "id") long id, // validating >1 values
    BindingResult bindingResult) {
    
    ExampleResponseDto result = exampleFactory.create();
    return new ResponseEntity<>(result, result != null ? HttpStatus.CREATED : HttpStatus.CONFLICT);
} 
```

When passing via URL, it is required to add `@Validated` annotation to the controller.


#### Response Codes

Specify all returned response codes via `@ApiResponses` and `@ApiResponse`:

```java
@ApiResponses(value = {
        //@ApiResponse(code = <code>, message = "describe what happened", response = ResponseDto.class)
        @ApiResponse(code = 201, message = "Article added", response = CreateArticleResponseDto.class),
        @ApiResponse(code = 400, message = "Parameter validation error", response = ApiErrorDto.class)
        //...
})
```

#### REST function with body parameters - full example

```java
    @ApiOperation(value = "Add new article", response = CreateArticleResponseDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Article added", response = CreateArticleResponseDto.class),
            @ApiResponse(code = 400, message = "Parameter validation error", response = ApiErrorDto.class)
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ResponseEntity<CreateArticleResponseDto> createArticle(
            @ApiParam(name = "id") @Valid @RequestBody CreateArticleRequestDto request,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InvalidBodyException(bindingResult);
        }
        final Article result = this.articleService.create(request);
        return new ResponseEntity<>(new CreateArticleResponseDto(result), HttpStatus.CREATED);
    }
```