using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace ParkingManagement.WebContent.UsersManagement
{
    public partial class WebForm1 : System.Web.UI.Page
    {
        protected void Page_Load(object sender, EventArgs e)
        {
            
            // Ensure user has logged in!!!
            string key = ManageUsers.API_KEY;
            if (key == null || key.Length != 40)
                Response.Redirect(ResolveUrl("~/login.aspx"));

            if (!Page.IsPostBack)
            {
                populateUserRoles(ddlUserRoles);
                clearData();
                
            }
         }

        /// <summary>
        /// Reset all fields
        /// </summary>
        private void clearData()
        {
            // Clear all text
            txtFirstName.Text = string.Empty;
            txtLastName.Text = string.Empty;
            txtEmail.Text = string.Empty;
            txtUserName.Text = string.Empty;
            txtPassword.Text = string.Empty;
            txtConfirmPassword.Text = string.Empty;
            chkAdmin.Checked = false;
        }

        /// <summary>
        /// Populate userRoles
        /// </summary>
        private void populateUserRoles(DropDownList ddlTarget)
        {
            List<ManageUsers.UserRoles> dtRoles = ManageUsers.getUserRoles();
            ddlTarget.DataSource = dtRoles;
            ddlTarget.DataTextField = "Description";
            ddlTarget.DataValueField = "RoleId";
            ddlTarget.DataBind();
        }


        /// <summary>
        /// Add a new user to the database
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        protected void btnSave_Click(object sender, EventArgs e)
        {
            int isAdmin = 0;
            if (chkAdmin.Checked)
                isAdmin = 1;

            ManageUsers.User newUser = new ManageUsers.User(txtUserName.Text.Trim(), Convert.ToInt32(ddlUserRoles.SelectedValue),
               txtFirstName.Text.Trim(), txtLastName.Text.Trim(), isAdmin, txtEmail.Text.Trim(), txtPassword.Text.Trim());
            ManageUsers.addUser(newUser);


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
            clearData();
            // go to the ManagerUsers page
            Response.Redirect(ResolveUrl("~\\WebContent\\UsersManagement\\UsersManagement.aspx"));
        }

      
      
    }
}