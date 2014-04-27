using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Net;
using System.IO;
using Newtonsoft.Json;
using ParkingManagement.WebContent.UsersManagement;


namespace ParkingManagement.WebContent.Reports
{
    public partial class Report1 : System.Web.UI.Page
    {
        private class UserReports
        {
            public string UserName { get; set; }
            public string FirstName { get; set; }
            public string LastName { get; set; }
            public string LotId { get; set; }
            public string DateTimeIn { get; set; }
            public string DateTimeOut { get; set; }
            public string Duration { get; set; }


        }

        protected void Page_Load(object sender, EventArgs e)
        {
           
            // Ensure user has logged in!!!
            string key = ManageUsers.API_KEY;
            if (key == null || key.Length != 40)
                Response.Redirect(ResolveUrl("~/index.aspx"));

            if (!Page.IsPostBack)
            {
                gvUsers.DataSource = getData();
                gvUsers.DataBind();
        
            }
           
        }

        private List<UserReports> getData()
        {
            HttpWebRequest req = WebRequest.Create(ManageUsers.serverAddress + "/reservations") as HttpWebRequest;
            req.ContentType = "application/json";
            req.Method = WebRequestMethods.Http.Get;

           req.Headers.Add("Authorization", ManageUsers.API_KEY);

            string result;
            List<UserReports> data;

            using (HttpWebResponse resp = req.GetResponse() as HttpWebResponse)
            {
                StreamReader reader = new StreamReader(resp.GetResponseStream());
                result = reader.ReadToEnd();
                result.Trim();
                data = (List<UserReports>)JsonConvert.DeserializeObject(result, typeof(List<UserReports>));

            }
            return data;
        }
    }
}