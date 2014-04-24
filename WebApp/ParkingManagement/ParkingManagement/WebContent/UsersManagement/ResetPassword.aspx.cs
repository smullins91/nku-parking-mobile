using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace ParkingManagement.WebContent.UsersManagement
{
    public partial class ResetPassword : System.Web.UI.Page
    {
        protected void Page_Load(object sender, EventArgs e)
        {
            
            // Ensure user has logged in!!!
            string key = ManageUsers.API_KEY;
            if (key == null || key.Length != 40)
                Response.Redirect(ResolveUrl("~/index.aspx"));
         }



        /// <summary>
        /// Reset password
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        protected void btnSave_Click(object sender, EventArgs e)
        {
            int userId = Convert.ToInt32(Session["userId"]);
            string userName = Session["username"].ToString();
            string lastName = Session["lastname"].ToString();
            string firstName = Session["firstname"].ToString();
            string email = Session["email"].ToString();
            int roleId = Convert.ToInt32(Session["roleId"]);
            int isAdmin = 0;
            if (Session["isAdmin"].ToString().Equals("Yes"))
                isAdmin = 1;

            // get the update user info
            ManageUsers.editUser u = new ManageUsers.editUser(userId, userName, roleId, firstName, lastName, email, isAdmin, txtPassword1.Text.Trim());

            ManageUsers.updateUser(u);

            Session.Clear();

            // go to the ManagerUsers page
            Response.Redirect(ResolveUrl("~\\WebContent\\UsersManagement\\UsersManagement.aspx"));
        }


        /// <summary>
        /// Clear data and go to user management page
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        protected void btnCancel_Click(object sender, EventArgs e)
        {
            // go to the ManagerUsers page
            Response.Redirect(ResolveUrl("~\\WebContent\\UsersManagement\\UsersManagement.aspx"));
        }

      

      
      
    }
}