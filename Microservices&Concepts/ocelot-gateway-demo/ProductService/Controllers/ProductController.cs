using Microsoft.AspNetCore.Mvc;
     using System.Collections.Generic;

     namespace ProductService.Controllers
     {
         [ApiController]
         [Route("api/[controller]")]
         public class ProductController : ControllerBase
         {
             [HttpGet]
             public IActionResult GetProducts()
             {
                 var products = new List<string> { "Laptop", "Phone", "Tablet" };
                 return Ok(products);
             }
         }
     }