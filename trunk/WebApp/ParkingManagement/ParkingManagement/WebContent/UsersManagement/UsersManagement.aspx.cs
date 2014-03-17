using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Data;


namespace ParkingManagement.WebContent.UsersManagement
{
    public partial class UsersManagement : System.Web.UI.Page
    {
        protected void Page_Load(object sender, EventArgs e)
        {
            if (!Page.IsPostBack)
            {
                populateGridview();
                populateUserRoles();
            }
           
        }

        /// <summary>
        /// Populate the gridview with user information
        /// </summary>
        private void populateGridview()
        {
            List<ManageUsers.SuperUser> dtUsers = ManageUsers.getUsers();
            gvUsers.DataSource = dtUsers;
            gvUsers.DataBind();
        }


        /// <summary>
        /// Populate userRoles
        /// </summary>
        private void populateUserRoles()
        {
            List<ManageUsers.UserRoles> dtRoles = ManageUsers.getUserRoles();
            ddlUserRoles.DataSource = dtRoles;
            ddlUserRoles.DataTextField = "Description";
            ddlUserRoles.DataValueField = "RoleId";
            ddlUserRoles.DataBind();
        }
      
        /// <summary>
        /// To add a new user in the database
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        protected void btnSave_Click(object sender, EventArgs e)
        {
            
            //ManageUsers.User newUser = new ManageUsers.User("komlavi3","komlavi3",3,"komlavi","ekouevi", "ekouevik1@nku.edu");
            ManageUsers.User newUser = new ManageUsers.User(txtUserName.Text, txtPassword.Text, Convert.ToInt32(ddlUserRoles.SelectedValue) ,
               txtFirstName.Text, txtLastName.Text, txtEmail.Text);
            ManageUsers.addUser(newUser);

            // go to the ManagerUsers page
            Response.Redirect("UsersManagement.aspx");
        }
    }
}