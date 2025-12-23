using Microsoft.AspNetCore.Mvc;
     using System.Collections.Generic;

     namespace OrderService.Controllers
     {
         [ApiController]
         [Route("api/[controller]")]
         public class OrderController : ControllerBase
         {
             [HttpGet]
             public IActionResult GetOrders()
             {
                 var orders = new List<string> { "Order1", "Order2", "Order3" };
                 return Ok(orders);
             }
         }
     }