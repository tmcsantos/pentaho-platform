package org.pentaho.platform.web.http.api.resources;

import org.junit.After;

import org.junit.Before;
import org.junit.Test;
import org.pentaho.platform.web.http.api.resources.services.RepositoryService;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import java.net.URI;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

public class RepositoryResourceTest {

  RepositoryResource repositoryResource;

  @Before
  public void setUp() {
    repositoryResource = spy( new RepositoryResource() );
    repositoryResource.repositoryService = mock( RepositoryService.class );
    repositoryResource.httpServletRequest = mock( HttpServletRequest.class );

  }

  @After
  public void tearDown() {
    repositoryResource = null;
  }

  @Test
  public void testDoExecuteDefault() throws Exception {
    String pathId = "pathId";

    StringBuffer requestUrl = new StringBuffer();
    doReturn( requestUrl ).when( repositoryResource.httpServletRequest ).getRequestURL();

    String queryString = "queryString";
    doReturn( queryString ).when( repositoryResource.httpServletRequest ).getQueryString();

    URI mockURI = null;
    doReturn( mockURI ).when( repositoryResource.repositoryService )
      .doExecuteDefault( pathId, requestUrl, queryString );

    Response mockResponse = mock( Response.class );
    doReturn( mockResponse ).when( repositoryResource ).buildSeeOtherResponse( mockURI );

    Response testResponse = repositoryResource.doExecuteDefault( pathId );
    assertEquals( mockResponse, testResponse );

    verify( repositoryResource.httpServletRequest, times( 1 ) ).getRequestURL();
    verify( repositoryResource.httpServletRequest, times( 1 ) ).getQueryString();
    verify( repositoryResource.repositoryService, times( 1 ) ).doExecuteDefault( pathId, requestUrl, queryString );
    verify( repositoryResource, times( 1 ) ).buildSeeOtherResponse( mockURI );
  }

  @Test
  public void testDoFormPost() throws Exception {
    String contextId = "contextId";
    String resourceId = "resourceId";
    MultivaluedMap<String, String> formParams = mock( MultivaluedMap.class );

    doReturn( repositoryResource.httpServletRequest ).when( repositoryResource ).correctPostRequest( formParams );

    Response mockResponse = mock( Response.class );
    doReturn( mockResponse ).when( repositoryResource ).doService( contextId, resourceId );

    Response testResponse = repositoryResource.doFormPost( contextId, resourceId, formParams );
    assertEquals( mockResponse, testResponse );

    verify( repositoryResource, times( 1 ) ).correctPostRequest( formParams );
    verify( repositoryResource, times( 1 ) ).doService( contextId, resourceId );
  }

  @Test
  public void testDoGet() throws Exception {
    String contextId = "contextId";
    String resourceId = "resourceId";

    Response mockResponse = mock( Response.class );
    doReturn( mockResponse ).when( repositoryResource ).doService( contextId, resourceId );

    Response testResponse = repositoryResource.doGet( contextId, resourceId );
    assertEquals( mockResponse, testResponse );

    verify( repositoryResource, times( 1 ) ).doService( contextId, resourceId );
  }
}