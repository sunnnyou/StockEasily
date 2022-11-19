export const enum ATarget {
    Blank = '_blank', // open in a new window or tab
    Self = '_self', // open in same frame as it was clicked (default)
    Parent = '_parent', // open in parent frame
    Top = '_top' // open in full body of window
}