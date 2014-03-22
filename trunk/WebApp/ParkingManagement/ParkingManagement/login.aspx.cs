using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using ParkingManagement.WebContent.UsersManagement; 

namespace ParkingManagement
{
    public partial class login : System.Web.UI.Page
    {
        protected void Page_Load(object sender, EventArgs e)
        {

        }

        protected void btnLogin_Click(object sender, EventArgs e)
        {
            ManageUsers.Login newLogin = new ManageUsers.Login(txtUsername.Text.Trim(), txtPassword.Text.Trim());

            if (ManageUsers.login(newLogin))
            {
                Response.Redirect( ResolveUrl("~\\WebContent\\Map\\Map.aspx"));
            }
            else lblError.Visible = true;
        }
    }
}