package Server;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.twilio.exception.ApiException;

import common.DBController;
import logic.Employer;




/**
 * BiteMe
 *
 * <p>No description provided (generated by Swagger Codegen https://github.com/swagger-api/swagger-codegen)
 *
 * API tests for HrApi 
 */
public class HrApiTest {
	private Response response;
    
	@Before
	public void setUp() throws Exception {
		EchoServer.con = DBController.getMySQLConnection("jdbc:mysql://localhost/biteme?serverTimezone=IST", "root",
				"MoshPe2969999");
		response = new Response();
	}

//    /**
//     * Approve business account
//     *
//     * @throws ApiException
//     *          if the Api call fails
//     */
//    @Test
//    public void approveBusinessAccountTest() {
//        String userName = "b";
//        HrApiService.approveBusinessAccount(userName, response);
//        assertEquals("Success in approving a new business account -> UserName: " + userName, response.getDescription());
//    }
    /**
     * Get all business acccounts that has isApproved &#x3D; &#x27;false&#x27;
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void getAllUnapprovedBusinessAccountTest() {
        Long branchManagerID = null;
        //List<BusinessAccount> response = api.getAllUnapprovedBusinessAccount(branchManagerID);
        //assertNotNull(response);
        // TODO: test validations
        
        
    }
//    /**
//     * Register new Buisness
//     *
//     * @throws ApiException
//     *          if the Api call fails
//     */
//    @Test
//    public void registerBusinessTest() {
//        Employer body = new Employer("intel", false, "John", "i", 1);
//        HrApiService.registerEmployer(body, response);
//        assertEquals("Success in adding a new employer -> BusinessName: intel", response.getDescription());
//        
//    }
}
